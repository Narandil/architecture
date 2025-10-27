package com.wgt.imt.architecture.interfaces.rest.clients;

import com.wgt.imt.architecture.interfaces.rest.clients.model.input.ClientInput;
import com.wgt.imt.architecture.interfaces.rest.clients.model.input.ClientUpdateInput;
import com.wgt.imt.architecture.interfaces.rest.clients.model.output.ClientOutput;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/imt/v1/clients")
public class ClientsController {

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ClientOutput> getAll() {
        return Set.of(
                ClientOutput.builder()
                        .identifier(UUID.randomUUID().toString())
                        .lastname("Doe")
                        .firstname("John")
                        .genre("M")
                        .build(),
                ClientOutput.builder()
                        .identifier(UUID.randomUUID().toString())
                        .lastname("Smith")
                        .firstname("Jane")
                        .genre("F")
                        .build()
        );
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientOutput create(@RequestBody final ClientInput client) {
        return ClientOutput.builder()
                .identifier(UUID.randomUUID().toString())
                .lastname(client.getLastname())
                .firstname(client.getFirstname())
                .genre(client.getGenre())
                .build();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientOutput getOne(@PathVariable("idClient") final String identifier) {
        return ClientOutput.builder()
                .identifier(identifier)
                .lastname("Smith")
                .firstname("Jane")
                .genre("F")
                .build();
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{idClient}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("idClient") final String identifier, @RequestBody final ClientUpdateInput client) {

    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{idClient}")
    public void delete(@PathVariable("idClient") final String identifier) {

    }
}
