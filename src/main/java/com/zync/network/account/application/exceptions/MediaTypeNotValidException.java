package com.zync.network.account.application.exceptions;

import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.Error;

public class MediaTypeNotValidException extends AbstractSystemException {
    @Override
    public Error getError() {
        return Error.MEDIA_TYPE_NOT_VALID;
    }
}
