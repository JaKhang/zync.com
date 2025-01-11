package com.zync.network.account.api.request;

public record VerifyRequest(String email, String code) {
}
