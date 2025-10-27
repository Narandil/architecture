package com.wgt.imt.architecture.infrastructures.bdd.clients;

import com.wgt.imt.architecture.business.clients.model.Client;
import com.wgt.imt.architecture.infrastructures.bdd.clients.repositories.ClientRepository;
import com.wgt.imt.architecture.infrastructures.bdd.clients.repositories.entities.ClientEntity;
import com.wgt.imt.architecture.infrastructures.bdd.clients.repositories.mappers.ClientBddMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientsBddService {
    private ClientRepository repository;
    private ClientBddMapper mapper;

    public boolean exist(final UUID identifier){
        return Optional.ofNullable(identifier)
                .map(UUID::toString)
                .map(this.repository::existsById)
                .orElse(false);
    }

    public Collection<Client> getAll() {
        return Objects.requireNonNullElse(this.repository.findAll(), Collections.<ClientEntity>emptyList())
                .stream()
                .map(this.mapper::from)
                .collect(Collectors.toSet());
    }

    public Optional<Client> get(final UUID identifier){
        return Optional.ofNullable(identifier)
                .map(UUID::toString)
                .flatMap(this.repository::findById)
                .map(this.mapper::from);
    }

    public Client save(final Client client){
        Objects.requireNonNull(client, "Impossible de sauvegarder un client nul");
        return this.mapper.from(
                this.repository.save(
                        this.mapper.to(client)
                )
        );
    }

    public void delete(final UUID identifier){
        Optional.ofNullable(identifier)
                .map(UUID::toString)
                .ifPresent(this.repository::deleteById);
    }
}
