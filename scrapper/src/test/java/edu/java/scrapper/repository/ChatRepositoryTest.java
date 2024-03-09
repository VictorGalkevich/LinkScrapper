package edu.java.scrapper.repository;

import edu.java.scrapper.entity.Chat;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatRepositoryTest {
    private final ChatRepository userRepository = new ChatRepository();

    @Test
    void testAddPositive() {
        Chat user = Chat.builder()
            .id(123L)
            .links(new ArrayList<>())
            .build();
        assertEquals(user, userRepository.save(user));
    }

    @Test
    void testFindByIdExistsPositive() {
        Chat user = Chat.builder()
            .id(123L)
            .links(new ArrayList<>())
            .build();
        userRepository.save(user);
        assertEquals(user, userRepository.findById(123L).get());
    }

    @Test
    void testFindByIdDoesNotExist() {
        assertTrue(userRepository.findById(100L).isEmpty());
    }
}
