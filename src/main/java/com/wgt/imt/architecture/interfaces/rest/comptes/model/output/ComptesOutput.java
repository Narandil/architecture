package com.wgt.imt.architecture.interfaces.rest.comptes.model.output;

import com.wgt.imt.architecture.interfaces.rest.common.model.output.AbstractOutput;
import lombok.*;

import java.io.Serial;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ComptesOutput extends AbstractOutput {
    @Serial
    private static final long serialVersionUID = 7079932239043880351L;
    private final String identifier;
    private final String name;
    private final String type;
    private final Double solde;
}