package com.rikishi.rikishi.repository;

import com.rikishi.rikishi.model.Indexable;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Repository<T extends Indexable<ID>, ID> {
    Optional<T> findById(ID id);
    Stream<T> find(Predicate<T> predicate);

    default Stream<T> findAll() {
        return find(t -> true);
    }

    T removeById(ID id);

    T add(T value);
    void addAll(Iterable<T> it);

    boolean remove(T value);

    void removeAll();

    void load() throws IOException;
    void save() throws IOException;
}
