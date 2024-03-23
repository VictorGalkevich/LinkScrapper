package edu.java.scrapper.repository;

import java.util.Optional;

public interface EntityRepository<T, K> {
    T save(T obj);

    Optional<T> findById(K identifier);

    T delete(T object);
}
