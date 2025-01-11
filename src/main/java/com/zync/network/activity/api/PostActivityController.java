package com.zync.network.activity.api;

import com.zync.network.activity.application.payload.ActivityPayload;
import com.zync.network.activity.application.queries.FindPostsActivitiesQuery;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.activity.domain.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostActivityController {

}
