package com.zync.network.account.api.controller;

import com.zync.network.account.api.controller.request.*;
import com.zync.network.account.application.commands.*;
import com.zync.network.account.application.model.AuthenticationPayload;
import com.zync.network.account.application.queries.CheckEmailQuery;
import com.zync.network.account.domain.aggregates.account.CodeType;
import com.zync.network.account.domain.aggregates.role.Role;
import com.zync.network.account.domain.aggregates.role.RoleName;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@RestController()
@RequestMapping("/api/v1/auth")
public class AuthController {
    Mediator mediator;
    Parser uaParser;

    @PostMapping("/authenticate")
    public AuthenticationPayload authenticate(HttpServletRequest request, @RequestBody AuthenticateRequest authenticateRequest) {
        Client client = uaParser.parse(request.getHeader("user-agent"));
        return mediator.send(new AuthenticateCommand(
                authenticateRequest.email(),
                authenticateRequest.password(),
                authenticateRequest.twoFactorCode(),
                client.os.family,
                client.userAgent.family,
                request.getRemoteAddr()
        ));
    }

    @GetMapping("/devices")
    public Map<String, Object> authenticate(@RequestHeader("user-agent") String userAgent, HttpServletRequest request) {
        Client client = uaParser.parse(userAgent);
        Map<String, Object> data = new HashMap<>();
        data.put("os", client.os);
        data.put("device", client.device);
        data.put("browser", client.userAgent);
        data.put("ip", request.getRemoteAddr());
        return data;

    }



    @PutMapping("/verification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestVerifiedCode(@RequestParam String email) {
        mediator.send(new RequestCodeCommand(email, CodeType.VERIFY));

    }

    @PostMapping("/verification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleVerify(@RequestBody VerifyRequest request) {
        mediator.send(new VerifyCommand(request.email(), request.code()));

    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleResetPassword(@RequestBody ResetPasswordRequest request) {
        mediator.send(new ResetPasswordCommand(request.email(), request.code(), request.newPassword()));

    }

    @PutMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleRequestRequestPasswordCode(@RequestParam String email) {
        mediator.send(new RequestCodeCommand(email, CodeType.RESET_PASSWORD));

    }

    @PostMapping("/reauthenticate")
    public AuthenticationPayload accessToken(@RequestBody AccessTokenRequest request, @RequestHeader("user-agent") String userAgent) {
        Client client = uaParser.parse(userAgent);
        return mediator.send(new ReAuthenticateCommand(request.refreshToken(), client.os.family,
                client.device.family,
                client.userAgent.family));
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ZID handlerRegister(@RequestBody RegisterRequest request){
       return mediator.send(new RegisterCommand(request.email(), request.password(), Set.of(RoleName.USER)));
    }

    @GetMapping("/check-email")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean handleCheckEmail(@RequestParam String email){
        return mediator.send(new CheckEmailQuery(email));
    }


}
