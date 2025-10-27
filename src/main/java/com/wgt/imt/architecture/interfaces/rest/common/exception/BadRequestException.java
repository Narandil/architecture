package com.wgt.imt.architecture.interfaces.rest.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Exception levée lorsqu'une requête est mal formée ou invalide.
 * Correspond au code HTTP 400 BAD REQUEST.
 * Utilisée notamment pour les erreurs de validation de contraintes.
 *
 * @author Emmanuel WAGUET
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
@ToString
public class BadRequestException extends AbstractRestException {

    @Serial
    private static final long serialVersionUID = 7177402494075491017L;

    /**
     * Statut HTTP 400
     */
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    /**
     * Message d'erreur décrivant le problème de validation
     */
    private final String message;
}
