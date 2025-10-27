package com.wgt.imt.architecture.infrastructures.events.comptes;

import com.wgt.imt.architecture.business.comptes.model.Compte;
import com.wgt.imt.architecture.infrastructures.events.comptes.model.MouvementCompteEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * Publisher pour les événements de compte avec solde négatif.
 * Publie un événement lorsqu'un compte passe en solde négatif.
 *
 * @author Emmanuel WAGUET
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class MouvementCompteEventPublisher {
    /**
     * Publisher d'événements Spring
     */
    private final ApplicationEventPublisher publisher;

    /**
     * Publie l'événement lorsqu'un mouvement est détecté.
     *
     * @param clientIdentifier l'identifiant du client propriétaire du compte
     * @param compte           le compte à vérifier
     * @throws NullPointerException si clientIdentifier ou compte est null
     */
    public void accept(final UUID clientIdentifier, final Compte compte) {
        Objects.requireNonNull(clientIdentifier, "Impossible de vérifier un compte avec un clientIdentifier nul");
        Objects.requireNonNull(compte, "Impossible de vérifier un compte nul");

        this.publisher.publishEvent(new MouvementCompteEvent(this, clientIdentifier, compte.getIdentifier()));
    }
}
