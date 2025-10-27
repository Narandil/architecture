package com.wgt.imt.architecture.interfaces.rest.comptes;

import com.wgt.imt.architecture.interfaces.rest.comptes.model.input.CompteInput;
import com.wgt.imt.architecture.interfaces.rest.comptes.model.input.CompteUpdateInput;
import com.wgt.imt.architecture.interfaces.rest.comptes.model.output.ComptesOutput;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/imt/v1/clients/{idClient}/comptes")
public class ComptesController {

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ComptesOutput> getAll(@PathVariable("idClient") final String clientIdentifier) {
        return Set.of(
                ComptesOutput.builder()
                        .identifier(UUID.randomUUID().toString())
                        .name("Compte Courant")
                        .type("CC")
                        .solde(1500.0)
                        .build(),
                ComptesOutput.builder()
                        .identifier(UUID.randomUUID().toString())
                        .name("Livret A")
                        .type("LA")
                        .solde(3000.0)
                        .build()
        );
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ComptesOutput create(@PathVariable("idClient") final String clientIdentifier, @RequestBody final CompteInput compte) {
        return ComptesOutput.builder()
                .identifier(UUID.randomUUID().toString())
                .name(compte.getName())
                .type(compte.getType())
                .solde(0.0)
                .build();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{idCompte}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ComptesOutput getOne(@PathVariable("idClient") final String clientIdentifier, @PathVariable("idCompte") final String identifier) {
        return ComptesOutput.builder()
                .identifier(identifier)
                .name("Livret A")
                .type("LA")
                .solde(3000.0)
                .build();
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{idCompte}")
    public void update(@PathVariable("idClient") final String clientIdentifier, @PathVariable("idCompte") final String identifier, @RequestBody final CompteUpdateInput compte) {

    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{idCompte}")
    public void delete(@PathVariable("idClient") final String clientIdentifier, @PathVariable("idCompte") final String identifier) {

    }
}
