package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.entity.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUri(String uri);

    List<Link> findAllByLastUpdatedAtBefore(OffsetDateTime time);

    @Query("SELECT l FROM Link l JOIN l.chats c WHERE c.id = ?1")
    List<Link> findAllForChat(Long chatId);
}
