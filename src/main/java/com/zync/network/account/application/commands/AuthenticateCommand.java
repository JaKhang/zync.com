package com.zync.network.account.application.commands;

import com.zync.network.account.application.model.AuthenticationPayload;
import com.zync.network.core.mediator.Request;

public record AuthenticateCommand(String email, String password, String twoFactorCode, String os, String browser, String ip) implements Request<AuthenticationPayload> {
}
