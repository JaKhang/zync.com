package com.zync.network.core.exceptions;

public class InvalidNewPasswordException extends AbstractSystemException {
    @Override
    public Error getError() {
        return Error.NEW_PASSWORD_INVALID;
    }
}
