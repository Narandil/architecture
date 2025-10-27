package com.wgt.imt.architecture.interfaces.rest.common;

import com.wgt.imt.architecture.interfaces.rest.common.exception.AbstractRestException;
import com.wgt.imt.architecture.interfaces.rest.common.model.output.ExceptionOutput;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @ExceptionHandler(AbstractRestException.class)
    public ResponseEntity<ExceptionOutput> handleRestException(final HttpServletRequest request, final AbstractRestException exception) {
        return new ResponseEntity<>(
                ExceptionOutput.builder()
                        .type(exception.getType())
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(FORMATTER))
                        .path(request.getRequestURI() + Optional.ofNullable(request.getQueryString()).map(query -> "?" + query).orElse(Strings.EMPTY))
                        .build(),
                exception.getHttpStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionOutput> handleGenericException(final HttpServletRequest request, final Exception exception) {
        return new ResponseEntity<>(
                ExceptionOutput.builder()
                        .type(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(FORMATTER))
                        .path(request.getRequestURI() + Optional.ofNullable(request.getQueryString()).map(query -> "?" + query).orElse(Strings.EMPTY))
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
