package com.wgt.imt.architecture.interfaces.rest.comptes.model.input;

import com.wgt.imt.architecture.interfaces.rest.common.model.input.AbstractInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class CompteInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = -790440597537439877L;
    private String name;
    private String type;
}
