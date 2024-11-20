package com.zync.network.account.domain.exception;

import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.Error;

public class InvalidBearerTokenException extends AbstractSystemException {
    @Override
    public Error getError() {
        return Error.TOKEN_INVALID;
    }
}
