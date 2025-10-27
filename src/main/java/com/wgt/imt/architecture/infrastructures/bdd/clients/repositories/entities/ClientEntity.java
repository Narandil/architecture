package com.wgt.imt.architecture.infrastructures.bdd.clients.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection= "clients")
public class ClientEntity {

    @Id private String identifier;
    private String lastname;
    private String firstname;
    private String genre;
    private Collection<CompteEntity> comptes;
}
