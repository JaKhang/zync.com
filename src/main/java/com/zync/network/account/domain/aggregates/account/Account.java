package com.zync.network.account.domain.aggregates.account;

import com.zync.network.account.domain.aggregates.role.Role;
import com.zync.network.account.domain.events.*;
import com.zync.network.account.domain.exception.*;
import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.InvalidNewPasswordException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends AggregateRoot implements UserDetails {

    @Getter
    @Column(unique = true, nullable = false, length = 320)
    String email;
    @Getter
    String password;
    String username;
    LocalDateTime verifiedAt;
    LocalDateTime lastLoginAt;

    @Getter
    @ColumnDefault("false")
    boolean locked;

    @Getter
    @ColumnDefault("false")
    boolean twoFactorEnabled = false;

    private String secret;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<Device> devices = new HashSet<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "account")
    @OrderBy("createdAt desc")
    Set<Code> codes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = {@JoinColumn(name = "role_id")})
    Set<Role> roles = new HashSet<>();

    public Account(ZID id, String username, String email, String password, Set<Role> roles) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.registerEvents(new AccountCreatedEvents(this.getId(), this.getEmail(), this.getPassword(), roles));

    }

    public void addCode(String value, int age, CodeType type) {
        if (type == CodeType.VERIFY && isVerified())
            throw new AccountAlreadyVerifiedException();

        DomainEvent event = switch (type) {
            case VERIFY -> new VerifyCodeCreatedEvent(value, email, age);
            case RESET_PASSWORD -> new ResetPasswordCodeCreatedEvent(value, email, age);
            case TWO_STEP -> new TwoStepCodeCreatedEvent(value, email);
        };
        this.registerEvents(event)   ;
        codes.add(new Code(ZID.fast(), value, age, type, this));
    }

    public void enableTwoFactorEnabled() {
        twoFactorEnabled = true;
    }

    public void disableTwoFactorEnabled() {
        twoFactorEnabled = false;
    }

    public void verify(String value) {
        if (isVerified()) throw new AccountAlreadyVerifiedException();
        validateCode(value, CodeType.VERIFY);
        verifiedAt = LocalDateTime.now();
        codes.removeIf(code -> code.isType(CodeType.VERIFY));
        registerEvents(new AccountVerifiedEvent(email, value));
    }

    public void restPassword(String value, @NotNull String newPassword) {
        validateCode(value, CodeType.RESET_PASSWORD);
        password = newPassword;
        registerEvents(new PasswordResetEvent(email, value, newPassword));
    }



    public boolean isVerified() {
        return verifiedAt != null;
    }

    public boolean containsDevice(ZID deviceId) {
        return devices.contains(new Device(deviceId));
    }
    public boolean containsDevice(String os, String browse) {
        return devices.stream().anyMatch(device -> device.isSameDevice(os, browse));
    }

    public void addRole(ZID... rolesIds){
        for (ZID rolesId : rolesIds) {
            roles.add(new Role(rolesId));
        }
    }

    public void removeRole(ZID... rolesIds){
        for (ZID rolesId : rolesIds) {
            roles.add(new Role(rolesId));
        }
    }

    public ZID registerDevice( String os, String browser, String ip) {
        if (devices.stream().anyMatch(device -> device.isSameDevice(os, browser)))
            return null;
        Device device = new Device(ZID.fast(), os, browser, ip, this);
        devices.add(device);
        lastLoginAt = LocalDateTime.now();
        return device.getId();
    }

    public boolean unregisterDevice(ZID deviceId) {
        registerEvents(new DevicesUnregisteredEvent(getId(), Set.of(deviceId)));
        return devices.remove(new Device(deviceId));
    }

    public boolean unregisterDevices(Set<ZID> deviceIds) {
        registerEvents(new DevicesUnregisteredEvent(getId(), deviceIds));
        return devices.removeIf(device -> deviceIds.contains(device.getId()));
    }

    private void validateCode(String value, CodeType type) {
        var codes = this.codes
                .stream()
                .filter(c -> c.isType(type))
                .toList();
        for (Code code : codes) {
            if (code.isMatch(value)) {
                if (code.isExpired()) throw new CodeExpiredException();
                this.codes.remove(code);
                return;
            }
        }

        throw new InvalidCodeException();

    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    @Override
    public String toString() {
        return "Account{" +
                "usernameOrEmail='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().<GrantedAuthority>mapMulti((role, objectConsumer) -> role.authorities().forEach(objectConsumer)).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Device getDevice(String os, String browser) {
        return devices.stream().filter(d -> d.isSameDevice( os, browser)).findFirst().orElse(null);
    }

    @Override
    public boolean isEnabled() {
        return isVerified();
    }


    public void changePassword(String password, String newPassword) {
        Assert.notNull(newPassword, "new password must not be null");
        Assert.notNull(password, "password must not be null");
        if (!password.equals(this.password)) throw new InvalidPasswordException();
        if (password.equals(newPassword)) throw new InvalidNewPasswordException();
        this.password = newPassword;
        registerEvents(new PasswordChangedEvent(getId(), password, newPassword));
    }

    public void validateDevice(ZID deviceId, String os, String browser) {
        Device device = devices.stream().filter(d -> d.getId().equals(deviceId)).findFirst().orElseThrow(InvalidDeviceException::new);
        if (!device.isSameDevice(os, browser)) throw new InvalidDeviceException();
    }
}
