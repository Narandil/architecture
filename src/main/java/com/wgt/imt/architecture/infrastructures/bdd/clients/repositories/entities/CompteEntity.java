package com.wgt.imt.architecture.infrastructures.bdd.clients.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompteEntity {

    private String identifier;
    private String name;
    private String type;
    private Double solde;
}
