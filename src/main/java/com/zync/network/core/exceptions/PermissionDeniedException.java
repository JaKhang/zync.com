package com.zync.network.core.exceptions;

public class PermissionDeniedException extends AbstractSystemException{
    @Override
    public Error getError() {
        return null;
    }
}
