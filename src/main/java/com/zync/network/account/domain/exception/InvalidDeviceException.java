package com.zync.network.account.domain.exception;

import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.Error;

public class InvalidDeviceException extends AbstractSystemException {
    @Override
    public Error getError() {
        return Error.DEVICE_INVALID;
    }
}
