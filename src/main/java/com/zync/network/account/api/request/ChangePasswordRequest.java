package com.zync.network.account.api.request;

public record ChangePasswordRequest(String password, String newPassword) {
}
