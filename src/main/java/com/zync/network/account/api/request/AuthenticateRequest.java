package com.zync.network.account.api.request;

public record AuthenticateRequest(String email, String password, String twoFactorCode) {
}
