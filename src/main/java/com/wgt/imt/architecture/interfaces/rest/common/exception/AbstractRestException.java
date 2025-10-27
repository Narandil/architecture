package com.wgt.imt.architecture.interfaces.rest.common.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public abstract class AbstractRestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5846250763816026669L;

    public abstract HttpStatus getHttpStatus();
    public String getType(){
        return this.getClass().getSimpleName();
    }
}
