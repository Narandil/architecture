package com.wgt.imt.architecture.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionImtUtils {

    public static <T> Collection<T> append(final Collection<T> source, final T element){
        final List<T> result = new ArrayList<>(Objects.requireNonNullElse(source, Collections.emptyList()));
        result.add(element);
        return result;
    }

    public static <T> Collection<T> append(final Collection<T> source, final T... elements){
        final List<T> result = new ArrayList<>(Objects.requireNonNullElse(source, Collections.emptyList()));
        Stream.of(elements).forEach(result::add);
        return result;
    }

    public static <T> Collection<T> append(final Collection<T> source, final Collection<T> elements){
        final List<T> result = new ArrayList<>(Objects.requireNonNullElse(source, Collections.emptyList()));
        result.addAll(elements);
        return result;
    }
}
