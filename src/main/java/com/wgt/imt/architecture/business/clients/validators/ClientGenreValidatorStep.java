package com.wgt.imt.architecture.business.clients.validators;

import com.wgt.imt.architecture.business.clients.model.Client;
import com.wgt.imt.architecture.business.common.model.GenreEnum;
import com.wgt.imt.architecture.business.common.validators.AbstractValidatorStep;
import com.wgt.imt.architecture.interfaces.rest.common.exception.BadRequestException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientGenreValidatorStep extends AbstractValidatorStep<Client> {

    @Override
    public void check(final Client toValidate) {
        if(GenreEnum.INCONNU.equals(toValidate.getGenre())) {
            throw new BadRequestException(String.format("Le genre communiqué ne correspond pas aux valeurs acceptables : %s", GenreEnum.ACCEPTABLE_VALUES));
        }
    }
}
