package com.zync.network.account.api.request;

public record ResetPasswordRequest(String email, String code, String newPassword) {
}
