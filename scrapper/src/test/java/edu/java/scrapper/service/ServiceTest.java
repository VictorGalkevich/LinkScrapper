package edu.java.scrapper.service;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.repository.ChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class ServiceTest {
    protected static final Long MOCKED_CHAT_ID = 123L;
    protected ChatRepository chatRepository;

    @BeforeEach
    void init() {
        chatRepository = new ChatRepository();
    }

    @AfterEach
    void clean() {
        chatRepository.delete(Chat.builder().id(MOCKED_CHAT_ID).build());
    }
}
