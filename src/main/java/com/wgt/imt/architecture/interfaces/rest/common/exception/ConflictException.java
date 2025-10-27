package com.wgt.imt.architecture.interfaces.rest.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Exception levée lorsqu'il y a un conflit avec l'état actuel de la ressource.
 * Correspond au code HTTP 409 CONFLICT.
 * Utilisée notamment lorsqu'un client ou compte existe déjà.
 *
 * @author Emmanuel WAGUET
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
@ToString
public class ConflictException extends AbstractRestException {

    @Serial
    private static final long serialVersionUID = -2491545545851298898L;

    /**
     * Statut HTTP 409
     */
    private final HttpStatus httpStatus = HttpStatus.CONFLICT;

    /**
     * Message d'erreur décrivant le conflit
     */
    private final String message;
}
