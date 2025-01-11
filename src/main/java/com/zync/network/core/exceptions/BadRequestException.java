package com.zync.network.core.exceptions;

public class BadRequestException extends AbstractSystemException{
    @Override
    public Error getError() {
        return Error.BAD_REQUEST;
    }
}
