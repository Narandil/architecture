package com.wgt.imt.architecture.interfaces.rest.common.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Classe abstraite de base pour toutes les exceptions REST de l'application.
 * Définit le contrat pour les exceptions métier qui seront converties en réponses HTTP.
 *
 * @author Emmanuel WAGUET
 * @version 1.0
 */
public abstract class AbstractRestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5846250763816026669L;

    /**
     * Retourne le statut HTTP associé à cette exception.
     *
     * @return le code de statut HTTP approprié
     */
    public abstract HttpStatus getHttpStatus();

    /**
     * Retourne le type de l'exception (nom de la classe).
     *
     * @return le nom simple de la classe d'exception
     */
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
