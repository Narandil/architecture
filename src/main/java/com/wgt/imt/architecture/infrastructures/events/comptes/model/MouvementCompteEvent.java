package com.wgt.imt.architecture.infrastructures.events.comptes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class MouvementCompteEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -5205862949120431824L;
    private final UUID clientIdentifier;
    private final UUID compteIdentifier;

    public MouvementCompteEvent(final Object source, final UUID clientIdentifier, final UUID compteIdentifier) {
        super(source);
        this.clientIdentifier = clientIdentifier;
        this.compteIdentifier = compteIdentifier;
    }
}
