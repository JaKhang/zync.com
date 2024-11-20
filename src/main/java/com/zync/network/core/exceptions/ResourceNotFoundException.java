package com.zync.network.core.exceptions;

public class ResourceNotFoundException extends AbstractSystemException {

    private String resourceName;
    public String field;
    private String value;

    public ResourceNotFoundException(String resourceName, String field, String value) {
        this.resourceName = resourceName;
        this.field = field;
        this.value = value;
    }

    @Override
    public Error getError() {
        return Error.RESOURCE_NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return getError().getMessage().formatted(resourceName, resourceName + ":" + field + ":" + value);
    }
}
