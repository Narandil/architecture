package com.wgt.imt.architecture.business.clients;

import com.wgt.imt.architecture.business.clients.model.Client;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ClientsService {
    final Map<UUID, Client> clients = new HashMap<>();

    public Collection<Client> getAll() {
        return this.clients.values();
    }

    public Optional<Client> getOne(final UUID identifier) {
        return Optional.ofNullable(this.clients.get(identifier));
    }

    public Client create(final Client newClient) {
        this.clients.put(newClient.getIdentifier(), newClient);
        return newClient;
    }

    public void update(final Client updatedClient) {
        this.clients.put(updatedClient.getIdentifier(), updatedClient);
    }

    public void delete(final UUID identifier) {
        this.clients.remove(identifier);
    }
}
