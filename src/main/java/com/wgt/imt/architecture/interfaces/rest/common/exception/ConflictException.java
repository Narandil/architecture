package com.wgt.imt.architecture.interfaces.rest.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
@RequiredArgsConstructor
@ToString
public class ConflictException extends AbstractRestException {

    @Serial
    private static final long serialVersionUID = -2491545545851298898L;
    private final HttpStatus httpStatus = HttpStatus.CONFLICT;
    private final String message;
}
