package com.zync.network.account.application.commands;

import com.zync.network.account.application.model.AuthenticationPayload;
import com.zync.network.core.mediator.MediatorRequest;

public record AuthenticateCommand(String usernameOrEmail, String password, String twoFactorCode, String os, String browser, String ip) implements MediatorRequest<AuthenticationPayload> {
}
