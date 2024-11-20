package com.zync.network.account.domain.aggregates.account;

import com.zync.network.core.domain.AbstractEntity;
import com.zync.network.core.domain.ZID;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "devices")
public class Device extends AbstractEntity {
    String os;
    String browser;
    String ip;
    @Setter
    LocalDateTime loginAt;
    @Getter
    boolean trusted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    Account account;

    public Device(ZID id, String os, String browser, String ip, Account account) {
        super(id);
        this.os = os;
        this.browser = browser;
        loginAt = LocalDateTime.now();
        this.account = account;
        this.ip = ip;
    }

    Device(ZID id) {
        super(id);
    }

    public void enableTrusted(){
        trusted = true;
    }

    public void disableTrusted(){
        trusted = false;
    }

    public boolean isSameDevice(String os, String browser) {
        return  this.os.equals(os) && this.browser.equals(browser);
    }


}
