package com.wgt.imt.architecture.business.comptes;

import com.wgt.imt.architecture.business.common.validators.ConstraintValidatorStep;
import com.wgt.imt.architecture.business.comptes.model.Compte;
import com.wgt.imt.architecture.business.comptes.validators.CompteAlreadyExistValidatorStep;
import com.wgt.imt.architecture.business.comptes.validators.CompteTypeValidatorStep;
import com.wgt.imt.architecture.infrastructures.bdd.clients.ClientsBddService;
import com.wgt.imt.architecture.infrastructures.events.comptes.MouvementCompteEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComptesServiceValidator extends ComptesService {

    public ComptesServiceValidator(final ClientsBddService service, final MouvementCompteEventPublisher mouvementPublisher) {
        super(service, mouvementPublisher);
    }

    public Compte create(final UUID clientIdentifier, final Compte newCompte) {
        new ConstraintValidatorStep<Compte>()
                .linkWith(new CompteAlreadyExistValidatorStep(this.service, clientIdentifier))
                .linkWith(new CompteTypeValidatorStep())
                .validate(newCompte)
                .throwIfInvalid();

        return super.create(clientIdentifier, newCompte);
    }

    public void update(final UUID clientIdentifier, final Compte updatedCompte) {
        new ConstraintValidatorStep<Compte>()
                .linkWith(new CompteTypeValidatorStep())
                .validate(updatedCompte)
                .throwIfInvalid();

        super.update(clientIdentifier, updatedCompte);
    }
}
