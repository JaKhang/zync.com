package com.zync.network.activity.api;

import com.zync.network.activity.application.commands.SeenActivitiesCommand;
import com.zync.network.activity.application.payload.ActivityPayload;
import com.zync.network.activity.application.queries.FindPostsActivitiesQuery;
import com.zync.network.activity.application.queries.FindUsersActivitiesQuery;
import com.zync.network.activity.domain.ActivityType;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ActivityController {
    private final Mediator mediator;

    @GetMapping("/me/activities")
    public List<ActivityPayload> findMyActivities(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "") Set<ActivityType> types
    ){
        return mediator.send(new FindUsersActivitiesQuery(principal.id(), limit, offset, types));
    }

    @GetMapping("/posts/{id}/activities")
    public List<ActivityPayload> findMyActivities(
            @PathVariable ZID id,
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "LIKE, REPOST") Set<ActivityType> types
    ){
        return mediator.send(new FindPostsActivitiesQuery(id, principal.id(), limit, offset, types));
    }

    @PutMapping("/me/activities")
    public void findMyActivities(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestParam() Set<ZID> ids
    ){
        mediator.send(new SeenActivitiesCommand(ids));
    }


}
