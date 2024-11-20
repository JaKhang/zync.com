package com.zync.network.account.api.controller;

import com.zync.network.account.api.controller.request.ChangePasswordRequest;
import com.zync.network.account.api.controller.request.DisableTwoStepLoginRequest;
import com.zync.network.account.application.commands.*;
import com.zync.network.account.application.model.DevicePayload;
import com.zync.network.account.application.queries.SignedDevicesQuery;
import com.zync.network.account.application.queries.SignedDevicesQueryHandler;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@RestController()
@RequestMapping("/api/v1/accounts")
public class AccountController {
    Mediator mediator;

    @DeleteMapping("devices")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unregisterDevice(@RequestParam Set<ZID> deviceIds, @AuthenticationPrincipal JwtPrincipal principal){
        mediator.send(new UnregisterDevicesCommand(principal.id(), deviceIds));
    }


    @PutMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody ChangePasswordRequest request, @AuthenticationPrincipal JwtPrincipal principal){
        mediator.send(new ChangePasswordCommand(principal.id(), request.password(), request.newPassword()));
    }

    @PutMapping("/two-step")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableTwoStepLogin(@AuthenticationPrincipal JwtPrincipal principal){
        mediator.send(new EnableTwoStepLoginCommand(principal.id()));
    }

    @DeleteMapping("/two-step")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableTwoStepLogin(@RequestBody DisableTwoStepLoginRequest request, @AuthenticationPrincipal JwtPrincipal principal){
        mediator.send(new DisableTwoStepLoginCommand(request.password(), principal.id()));
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleLogout(@AuthenticationPrincipal JwtPrincipal principal){
        mediator.send(new LogoutCommand(principal.id(), principal.deviceId()));
    }





}
