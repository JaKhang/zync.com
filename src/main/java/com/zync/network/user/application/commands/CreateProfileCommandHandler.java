package com.zync.network.user.application.commands;

import com.zync.network.account.application.clients.AccountClient;
import com.zync.network.account.application.clients.AccountRegisterRequest;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.domain.profile.Profile;
import com.zync.network.user.domain.profile.ProfileFactory;
import com.zync.network.user.domain.profile.ProfileRepository;
import org.springframework.transaction.annotation.Transactional;


public class CreateProfileCommandHandler implements RequestHandler<CreateProfileCommand, ZID> {

    AccountClient accountClient;
    ProfileRepository profileRepository;

    @Override
    @Transactional
    public ZID handle(CreateProfileCommand request) {
        ZID accountId = accountClient.register(new AccountRegisterRequest(request.email(), request.password()));
        Profile profile = ProfileFactory.crate(accountId, request.firstName(), request.middleName(), request.firstName(), request.dateOfBirth(), request.locale());
        return profileRepository.save(profile);
    }
}
