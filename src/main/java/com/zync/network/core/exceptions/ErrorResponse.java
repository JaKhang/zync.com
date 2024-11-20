package com.zync.network.core.exceptions;

public record ErrorResponse(int code, int status, String message) {

}
