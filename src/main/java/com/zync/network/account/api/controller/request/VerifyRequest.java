package com.zync.network.account.api.controller.request;

public record VerifyRequest(String email, String code) {
}
