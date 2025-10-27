package com.wgt.imt.architecture.interfaces.rest.comptes.model.output;

import com.wgt.imt.architecture.business.comptes.model.Compte;
import com.wgt.imt.architecture.interfaces.rest.common.model.output.AbstractOutput;
import lombok.*;

import java.io.Serial;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ComptesOutput extends AbstractOutput {

    public static ComptesOutput from(final Compte comptesOutput) {
        return ComptesOutput.builder()
                .identifier(comptesOutput.getIdentifier().toString())
                .name(comptesOutput.getName())
                .type(comptesOutput.getType().name())
                .solde(comptesOutput.getSolde())
                .build();
    }

    @Serial
    private static final long serialVersionUID = 7079932239043880351L;
    private final String identifier;
    private final String name;
    private final String type;
    private final Double solde;
}