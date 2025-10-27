package com.wgt.imt.architecture.interfaces.rest.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
@RequiredArgsConstructor
@ToString
public class NotFoundException extends AbstractRestException {

    @Serial
    private static final long serialVersionUID = 4833441532411808448L;
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private final String message;
}
