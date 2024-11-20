package com.zync.network.account.domain.exception;

import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.Error;

public class InvalidAccountIdentityException extends AbstractSystemException {

    @Override
    public Error getError() {
        // TODO (PC, 12/10/2024): To change the body of an implemented method
        return Error.BAD_ACCOUNT_IDENTITY;
    }
}
