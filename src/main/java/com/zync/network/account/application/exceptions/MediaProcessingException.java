package com.zync.network.account.application.exceptions;

import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.Error;

public class MediaProcessingException extends AbstractSystemException {
    @Override
    public Error getError() {
        return Error.UNKNOWN;
    }
}
