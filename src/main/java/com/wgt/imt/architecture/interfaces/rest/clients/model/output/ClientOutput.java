package com.wgt.imt.architecture.interfaces.rest.clients.model.output;

import com.wgt.imt.architecture.interfaces.rest.common.model.output.AbstractOutput;
import lombok.*;

import java.io.Serial;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ClientOutput extends AbstractOutput {

    @Serial
    private static final long serialVersionUID = -5881478654611574936L;
    private final String identifier;
    private final String lastname;
    private final String firstname;
    private final String genre;
}
