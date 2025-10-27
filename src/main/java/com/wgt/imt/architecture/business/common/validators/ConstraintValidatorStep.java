package com.wgt.imt.architecture.business.common.validators;

import com.wgt.imt.architecture.interfaces.rest.common.exception.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ConstraintValidatorStep<T> extends AbstractValidatorStep<T> {

    @Override
    public void check(final T toValidate) {
        try(ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()){
            final Set<ConstraintViolation<T>> violations = validatorFactory.getValidator().validate(toValidate);

            if(!violations.isEmpty()){
                throw new BadRequestException(String.format(
                        "Au moins une contrainte sur l'objet n'est pas respectée : %s",
                        violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")))
                );
            }
        }
    }
}
