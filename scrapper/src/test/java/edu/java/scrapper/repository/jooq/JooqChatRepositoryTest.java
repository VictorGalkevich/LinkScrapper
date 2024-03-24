package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperIT;
import edu.java.scrapper.entity.Chat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.OffsetDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ScrapperIT
public class JooqChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqChatRepository jooqChatRepository;
    private static final Chat TEST_CHAT;
    private static final Long TEST_ID = 123L;

    static {
        TEST_CHAT = Chat.builder()
            .id(123L)
            .links(null)
            .createdAt(OffsetDateTime.now())
            .build();
    }

    @Test
    void testAddPositive() {
        Chat save = jooqChatRepository.save(TEST_CHAT);
        assertEquals(TEST_CHAT.getId(), save.getId());
        assertEquals(TEST_CHAT.getLinks(), save.getLinks());
    }

    @Test
    void testFindByIdExistsPositive() {
        jooqChatRepository.save(TEST_CHAT);
        Optional<Chat> found = jooqChatRepository.findById(TEST_ID);
        assertTrue(found.isPresent());
    }

    @Test
    void testFindByIdDoesNotExist() {
        assertTrue(jooqChatRepository.findById(TEST_ID).isEmpty());
    }

}
