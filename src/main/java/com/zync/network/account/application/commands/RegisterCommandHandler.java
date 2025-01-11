package com.zync.network.account.application.commands;

import com.zync.network.account.application.exceptions.EmailAlreadyUsedException;
import com.zync.network.account.application.exceptions.UsernameAlreadyUsedException;
import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.aggregates.role.Role;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.account.domain.repositories.RoleRepository;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RegisterCommandHandler implements RequestHandler<RegisterCommand, ZID> {

    AccountRepository accountRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public ZID handle(RegisterCommand command) {
        System.out.println(command);
        if (accountRepository.existsByEmail(command.email()))
            throw new EmailAlreadyUsedException();
        if (accountRepository.existsByUsername(command.username()))
            throw new UsernameAlreadyUsedException();
        Set<Role> roles = roleRepository.findAllByNames(command.roleNames());
        Account account = new Account(ZID.fast(), command.username(), command.email(), passwordEncoder.encode(command.password()), roles);
        accountRepository.save(account);
        return account.getId();
    }
}
