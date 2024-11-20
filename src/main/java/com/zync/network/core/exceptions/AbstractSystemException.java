package com.zync.network.core.exceptions;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public abstract class AbstractSystemException extends RuntimeException {
    public abstract @NotNull Error getError();

    public AbstractSystemException(String message) {
        super(message);
    }



    public AbstractSystemException() {
    }

    @Override
    public String getMessage() {
        return getError().getMessage();
    }
}
