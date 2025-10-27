package com.wgt.imt.architecture.infrastructures.bdd.clients.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité MongoDB représentant un compte bancaire.
 * Document embarqué dans ClientEntity (pas de collection séparée).
 *
 * @author Emmanuel WAGUET
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompteEntity {

    /**
     * Identifiant unique du compte
     */
    private String identifier;

    /**
     * Nom du compte
     */
    private String name;

    /**
     * Type de compte au format String
     */
    private String type;

    /**
     * Solde du compte
     */
    private Double solde;
}
