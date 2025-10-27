package com.wgt.imt.architecture.infrastructures.events.comptes;

import com.wgt.imt.architecture.business.comptes.model.Compte;
import com.wgt.imt.architecture.infrastructures.events.comptes.model.MouvementCompteEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MouvementCompteEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void accept(final UUID clientIdentifier, final Compte compte){
        Objects.requireNonNull(clientIdentifier, "Impossible de vérifier un compte avec un clientIdentifier nul");
        Objects.requireNonNull(compte, "Impossible de vérifier un compte nul");

        this.publisher.publishEvent(new MouvementCompteEvent(this, clientIdentifier, compte.getIdentifier()));
    }
}
