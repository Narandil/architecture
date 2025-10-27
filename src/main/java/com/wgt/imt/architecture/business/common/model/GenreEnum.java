package com.wgt.imt.architecture.business.common.model;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public enum GenreEnum {
    HOMME, FEMME, NON_COMMUNIQUER, INCONNU;

    public static final String ACCEPTABLE_VALUES = Set.of(HOMME, FEMME, NON_COMMUNIQUER).stream().map(Enum::name).collect(Collectors.joining(", "));

    public static GenreEnum fromOrDefault(final String name){
        return GenreEnum.from(name).orElse(GenreEnum.INCONNU);
    }

    public static Optional<GenreEnum> from(final String name){
        try {
            return Optional.ofNullable(name)
                    .map(GenreEnum::valueOf);
        } catch (final IllegalArgumentException e){
            return Optional.empty();
        }
    }
}