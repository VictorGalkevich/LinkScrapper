package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.repository.jdbc.JdbcChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
public class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository userRepository;

    @Test
    void testAddPositive() {
        Chat user = Chat.builder()
                .id(123L)
                .links(null)
                .createdAt(OffsetDateTime.now())
                .build();
        Chat save = userRepository.save(user);
        assertEquals(user.getId(), save.getId());
        assertEquals(user.getLinks(), save.getLinks());
    }

    @Test
    void testFindByIdExistsPositive() {
        Chat user = Chat.builder()
                .id(123L)
                .links(null)
                .createdAt(OffsetDateTime.now())
                .build();
        Chat save = userRepository.save(user);
        assertEquals(user.getId(), save.getId());
        assertEquals(user.getLinks(), save.getLinks());
    }

    @Test
    void testFindByIdDoesNotExist() {
        assertTrue(userRepository.findById(100L).isEmpty());
    }
}
