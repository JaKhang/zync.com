package com.zync.network.account.api.controller.request;

public record ResetPasswordRequest(String email, String code, String newPassword) {
}
