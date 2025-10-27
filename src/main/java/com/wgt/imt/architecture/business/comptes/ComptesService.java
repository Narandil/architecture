package com.wgt.imt.architecture.business.comptes;

import com.wgt.imt.architecture.business.comptes.model.Compte;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComptesService {
    final Map<UUID, Compte> comptes = new HashMap<>();

    public Collection<Compte> getAllFilteredByClient(final UUID clientIdentifier) {
        return Objects.requireNonNullElse(this.comptes.values(), Collections.<Compte>emptySet()).stream()
                .filter(compte -> Objects.equals(compte.getClientIdentifier(), clientIdentifier))
                .collect(Collectors.toSet());
    }

    public Optional<Compte> getOneFilteredByClient(final UUID clientIdentifier, final UUID identifier) {
        return Optional.ofNullable(this.comptes.get(identifier))
                .filter(compte -> Objects.equals(compte.getClientIdentifier(), clientIdentifier));
    }

    public Compte create(final UUID clientIdentifier, final Compte newCompte) {
        this.comptes.put(newCompte.getIdentifier(), newCompte);
        return newCompte;
    }

    public void update(final UUID clientIdentifier, final Compte updatedCompte) {
        this.comptes.put(updatedCompte.getIdentifier(), updatedCompte);
    }

    public void delete(final UUID clientIdentifier, final UUID identifier) {
        this.comptes.remove(identifier);
    }
}
