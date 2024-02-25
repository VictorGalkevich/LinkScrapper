package edu.java.bot.repository;

import java.util.Optional;

public interface Repository<T, K> {
    T save(T obj);

    Optional<T> findById(K identifier);
}
