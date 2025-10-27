package com.wgt.imt.architecture.business.common.model;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public enum TypeCompteEnum {
    COMPTE_COURANT, LIVRET_A, LDDS, INCONNU;

    public static final String ACCEPTABLE_VALUES = Set.of(COMPTE_COURANT, LIVRET_A, LDDS).stream().map(Enum::name).collect(Collectors.joining(", "));

    public static TypeCompteEnum fromOrDefault(final String name) {
        return TypeCompteEnum.from(name).orElse(TypeCompteEnum.INCONNU);
    }

    public static Optional<TypeCompteEnum> from(final String name) {
        try {
            return Optional.ofNullable(name).map(TypeCompteEnum::valueOf);
        } catch (final IllegalArgumentException e){
            return Optional.empty();
        }
    }
}
