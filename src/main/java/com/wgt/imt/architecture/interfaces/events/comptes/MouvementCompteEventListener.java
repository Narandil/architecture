package com.wgt.imt.architecture.interfaces.events.comptes;

import com.wgt.imt.architecture.business.comptes.ComptesService;
import com.wgt.imt.architecture.infrastructures.events.comptes.model.MouvementCompteEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MouvementCompteEventListener implements ApplicationListener<MouvementCompteEvent> {

    private final ComptesService comptesService;

    @Async
    @Override
    public void onApplicationEvent(final MouvementCompteEvent event) {
        this.comptesService.applyAgios(event.getClientIdentifier(), event.getCompteIdentifier());
    }
}
