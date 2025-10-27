package com.wgt.imt.architecture.business.comptes.validators;

import com.wgt.imt.architecture.business.common.model.TypeCompteEnum;
import com.wgt.imt.architecture.business.common.validators.AbstractValidatorStep;
import com.wgt.imt.architecture.business.comptes.model.Compte;
import com.wgt.imt.architecture.interfaces.rest.common.exception.BadRequestException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CompteTypeValidatorStep extends AbstractValidatorStep<Compte> {

    @Override
    public void check(final Compte toValidate) {
        if(TypeCompteEnum.INCONNU.equals(toValidate.getType())) {
            throw new BadRequestException(String.format("Le type communiqué ne correspond pas aux valeurs acceptables : %s", TypeCompteEnum.ACCEPTABLE_VALUES));
        }
    }
}
