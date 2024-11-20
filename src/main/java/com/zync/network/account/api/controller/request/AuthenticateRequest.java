package com.zync.network.account.api.controller.request;

public record AuthenticateRequest(String email, String password, String twoFactorCode) {
}
