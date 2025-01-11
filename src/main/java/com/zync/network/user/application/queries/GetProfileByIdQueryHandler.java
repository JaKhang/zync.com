package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.application.models.ProfileMapper;
import com.zync.network.user.application.models.ProfilePayload;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.infrastructure.persistence.UserJpaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.util.Objects;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GetProfileByIdQueryHandler implements RequestHandler<GetProfileByIdQuery, ProfilePayload> {
    UserJpaRepository repository;
    ProfileMapper mapper;

    @Override
    public ProfilePayload handle(GetProfileByIdQuery request) {
        User user = repository.findById(request.userId()).orElseThrow(() -> new ResourceNotFoundException("USER", "id", request.userId()));
        User self = repository.findById(request.selfId()).orElseThrow(() -> new ResourceNotFoundException("USER", "id", request.userId()));
        if (!user.isPrivate() || isAuthenticatedUser(request.userId(), request.selfId()))
            return mapper.toPayLoad(user, self);

        if (Hibernate.contains(user.getFollowers(), request.selfId()))
            mapper.toPayLoad(user, self);


        return mapper.toPrivateProfile(user, self);
    }

    private boolean isAuthenticatedUser(ZID zid, ZID zid1) {
        return Objects.equals(zid1, zid);
    }


}
