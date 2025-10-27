package com.wgt.imt.architecture.interfaces.rest.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Exception levée lorsqu'une ressource demandée n'est pas trouvée.
 * Correspond au code HTTP 404 NOT FOUND.
 *
 * @author Emmanuel WAGUET
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
@ToString
public class NotFoundException extends AbstractRestException {

    @Serial
    private static final long serialVersionUID = 4833441532411808448L;

    /**
     * Statut HTTP 404
     */
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    /**
     * Message d'erreur décrivant la ressource non trouvée
     */
    private final String message;
}
