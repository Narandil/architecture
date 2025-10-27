package com.wgt.imt.architecture.infrastructures.events.comptes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.util.UUID;

/**
 * Événement publié lorsqu'un compte bancaire est mis a jour.
 * Utilisé dans le cadre du pattern Observer pour déclencher l'application d'agios.
 *
 * @author Emmanuel WAGUET
 * @version 1.0
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class MouvementCompteEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -5205862949120431824L;

    /**
     * Identifiant du client propriétaire du compte
     */
    private final UUID clientIdentifier;

    /**
     * Identifiant du compte sur lequel le mouvement a eu lieu
     */
    private final UUID compteIdentifier;

    /**
     * Constructeur de l'événement.
     *
     * @param source           la source de l'événement (généralement le publisher)
     * @param clientIdentifier l'identifiant du client
     * @param compteIdentifier l'identifiant du compte
     */
    public MouvementCompteEvent(final Object source, final UUID clientIdentifier, final UUID compteIdentifier) {
        super(source);
        this.clientIdentifier = clientIdentifier;
        this.compteIdentifier = compteIdentifier;
    }
}
