package edu.java.scrapper.repository;

import edu.java.scrapper.entity.Link;
import java.util.Optional;

public interface LinkUpdateRepository<T extends Link> {
    Optional<T> findLink(Link link);

    T save(T link);

    void update(T link);
}
