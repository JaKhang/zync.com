package com.zync.network.account.application.commands;

import com.zync.network.account.application.model.AuthenticationPayload;
import com.zync.network.core.mediator.MediatorRequest;

public record ReAuthenticateCommand(String refreshToken, String os, String deviceName, String browser) implements MediatorRequest<AuthenticationPayload> {
}
