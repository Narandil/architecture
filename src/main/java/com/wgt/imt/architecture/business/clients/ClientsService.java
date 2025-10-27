package com.wgt.imt.architecture.business.clients;

import com.wgt.imt.architecture.business.clients.model.Client;
import com.wgt.imt.architecture.infrastructures.bdd.clients.ClientsBddService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ClientsService {
    private ClientsBddService service;

    public Collection<Client> getAll() {
        return Objects.requireNonNullElse(this.service.getAll(), Collections.emptySet());
    }

    public Optional<Client> getOne(final UUID identifier) {
        return this.service.get(identifier);
    }

    public Client create(final Client newClient) {
        return this.service.save(newClient);
    }

    public void update(final Client updatedClient) {
        this.service.save(updatedClient);
    }

    public void delete(final UUID identifier) {
        this.service.delete(identifier);
    }
}
