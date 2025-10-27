package com.wgt.imt.architecture.business.common.model;

import com.wgt.imt.architecture.interfaces.rest.common.exception.AbstractRestException;
import com.wgt.imt.architecture.interfaces.rest.common.exception.BadRequestException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ValidatorResult {

    public static ValidatorResult valid(){
        return ValidatorResult.builder().isValid(true).build();
    }

    public static ValidatorResult invalid(final String message){
        return ValidatorResult.builder().isValid(false).exceptionToThrow(new BadRequestException(message)).build();
    }

    public static ValidatorResult invalid(final AbstractRestException exceptionToThrow){
        return ValidatorResult.builder().isValid(false).exceptionToThrow(exceptionToThrow).build();
    }

    private final boolean isValid;
    private final AbstractRestException exceptionToThrow;

    public void throwIfInvalid(){
        if(!this.isValid()){
            throw this.exceptionToThrow;
        }
    }
}
