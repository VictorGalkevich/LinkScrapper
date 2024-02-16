package edu.java.bot.repository;

import java.util.Optional;

public interface Repository<T, ID> {
    T save(T obj);
    Optional<T> findById(ID identifier);
}
