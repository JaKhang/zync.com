package com.zync.network.media.infrastructure.storage;

import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.Error;

public class StorageException extends AbstractSystemException {

    public StorageException(String message) {
        super(message);
    }

    @Override
    public Error getError() {
        return Error.UNKNOWN;
    }
}
