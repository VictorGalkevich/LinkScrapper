package edu.java.bot.repository;

import edu.java.bot.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepository();

    @Test
    void testAddPositive() {
        User user = User.builder()
            .id(123L)
            .links(new ArrayList<>())
            .build();
        assertEquals(user, userRepository.save(user));
    }

    @Test
    void testFindByIdExistsPositive() {
        User user = User.builder()
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
