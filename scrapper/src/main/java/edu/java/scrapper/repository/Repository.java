package edu.java.scrapper.repository;

import java.util.Optional;

public interface Repository<T, K> {
    T save(T obj);

    Optional<T> findById(K identifier);

    void delete(T object);
}
