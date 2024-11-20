package com.zync.network.account.api.controller.request;

public record ChangePasswordRequest(String password, String newPassword) {
}
