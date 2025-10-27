package com.wgt.imt.architecture.interfaces.rest.clients;

import com.wgt.imt.architecture.business.clients.ClientsService;
import com.wgt.imt.architecture.business.clients.ClientsServiceValidator;
import com.wgt.imt.architecture.business.clients.model.Client;
import com.wgt.imt.architecture.interfaces.rest.clients.model.input.ClientInput;
import com.wgt.imt.architecture.interfaces.rest.clients.model.input.ClientUpdateInput;
import com.wgt.imt.architecture.interfaces.rest.clients.model.output.ClientOutput;
import com.wgt.imt.architecture.interfaces.rest.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/imt/v1/clients")
public class ClientsController {
    private final ClientsServiceValidator service;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ClientOutput> getAll() {
        return Objects.requireNonNullElse(this.service.getAll(), Collections.<Client>emptySet()).stream()
                .map(ClientOutput::from)
                .collect(Collectors.toSet());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientOutput create(@RequestBody final ClientInput client) {
        return ClientOutput.from(
                this.service.create(
                        ClientInput.convert(client)
                )
        );
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientOutput getOne(@PathVariable("idClient") final String identifier) {
        return this.service.getOne(UUID.fromString(identifier))
                .map(ClientOutput::from)
                .orElseThrow(() -> new NotFoundException(String.format("Le client d'identifiant %s n'a pas été trouvé.", identifier)));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{idClient}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("idClient") final String identifier, @RequestBody final ClientUpdateInput client) {
        this.service.update(
                this.service.getOne(UUID.fromString(identifier))
                        .map(alreadySaved -> ClientUpdateInput.from(client, alreadySaved))
                        .orElseThrow(() -> new NotFoundException(String.format("Le client d'identifiant %s n'a pas été trouvé.", identifier)))
        );
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{idClient}")
    public void delete(@PathVariable("idClient") final String identifier) {
        this.service.delete(UUID.fromString(identifier));
    }
}
