package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c.id FROM Link l JOIN l.chats c WHERE l.id = ?1")
    List<Long> findAllChatIdsByLinkId(Long linkId);
}
