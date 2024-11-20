package com.zync.network.account.domain.exception;

import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.Error;

public class InvalidCodeException extends AbstractSystemException {
    @Override
    public Error getError() {
        return Error.CODE_INVALID;
    }
}
