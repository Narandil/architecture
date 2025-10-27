package com.wgt.imt.architecture.infrastructures.bdd.clients.repositories;

import com.wgt.imt.architecture.infrastructures.bdd.clients.repositories.entities.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<ClientEntity, String> {
}
