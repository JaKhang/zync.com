package com.zync.network.account.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record AuthenticationPayload(String accessToken, String refreshToken) {
}
