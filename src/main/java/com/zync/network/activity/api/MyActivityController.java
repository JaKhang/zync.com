package com.zync.network.activity.api;

import com.zync.network.activity.application.payload.ActivityPayload;
import com.zync.network.activity.application.queries.FindUsersActivitiesQuery;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.activity.domain.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class MyActivityController {
    private final Mediator mediator;




}
