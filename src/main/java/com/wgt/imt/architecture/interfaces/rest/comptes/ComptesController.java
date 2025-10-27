package com.wgt.imt.architecture.interfaces.rest.comptes;

import com.wgt.imt.architecture.business.comptes.ComptesService;
import com.wgt.imt.architecture.business.comptes.model.Compte;
import com.wgt.imt.architecture.interfaces.rest.comptes.model.input.CompteInput;
import com.wgt.imt.architecture.interfaces.rest.comptes.model.input.CompteUpdateInput;
import com.wgt.imt.architecture.interfaces.rest.comptes.model.output.ComptesOutput;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/imt/v1/clients/{idClient}/comptes")
public class ComptesController {
    private final ComptesService service;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ComptesOutput> getAll(@PathVariable("idClient") final String clientIdentifier) {
        return Objects.requireNonNullElse(this.service.getAllFilteredByClient(UUID.fromString(clientIdentifier)), Collections.<Compte>emptySet()).stream()
                .map(ComptesOutput::from)
                .collect(Collectors.toSet());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ComptesOutput create(@PathVariable("idClient") final String clientIdentifier, @RequestBody final CompteInput compte) {
        return ComptesOutput.from(
                this.service.create(
                        UUID.fromString(clientIdentifier),
                        CompteInput.convert(compte)
                )
        );
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{idCompte}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ComptesOutput getOne(@PathVariable("idClient") final String clientIdentifier, @PathVariable("idCompte") final String identifier) {
        return this.service.getOneFilteredByClient(UUID.fromString(clientIdentifier), UUID.fromString(identifier))
                .map(ComptesOutput::from)
                .orElseThrow(() -> new RuntimeException(String.format("Le compte d'identifiant %s n'a pas été trouvé pour le client %s.", identifier, clientIdentifier)));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{idCompte}")
    public void update(@PathVariable("idClient") final String clientIdentifier, @PathVariable("idCompte") final String identifier, @RequestBody final CompteUpdateInput compte) {
        final UUID formattedClientIdentifier = UUID.fromString(clientIdentifier);
        this.service.update(
                formattedClientIdentifier,
                this.service.getOneFilteredByClient(formattedClientIdentifier, UUID.fromString(identifier))
                        .map(alreadySaved -> CompteUpdateInput.from(compte, alreadySaved))
                        .orElseThrow(() -> new RuntimeException(String.format("Le compte d'identifiant %s n'a pas été trouvé pour le client %s.", identifier, clientIdentifier)))
        );

    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{idCompte}")
    public void delete(@PathVariable("idClient") final String clientIdentifier, @PathVariable("idCompte") final String identifier) {
        this.service.delete(UUID.fromString(clientIdentifier), UUID.fromString(identifier));
    }
}
