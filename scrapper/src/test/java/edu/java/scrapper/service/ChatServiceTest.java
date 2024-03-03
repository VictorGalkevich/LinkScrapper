package edu.java.scrapper.service;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.exception.ChatAlreadyRegisteredException;
import edu.java.scrapper.exception.ChatIsNotRegisteredException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class ChatServiceTest extends ServiceTest {
    private ChatService chatService;

    @Override
    void init() {
        super.init();
        chatService = new ChatService(chatRepository);
    }

    @Test
    void testDeleteWhenChatDoesNotExist() {
        assertThrows(ChatIsNotRegisteredException.class, () -> chatService.delete(MOCKED_CHAT_ID));
    }

    @Test
    void testRegisterSuccessful() {
        chatService.registerChat(MOCKED_CHAT_ID);
        Optional<Chat> chat = chatRepository.findById(MOCKED_CHAT_ID);
        assertTrue(chat.isPresent());
    }

    @Test
    void testRegisterWhenChatAlreadyExists() {
        chatService.registerChat(MOCKED_CHAT_ID);
        assertThrows(ChatAlreadyRegisteredException.class, () -> chatService.registerChat(MOCKED_CHAT_ID));
    }

    @Test
    void testDeleteSuccessful() {
        chatService.registerChat(MOCKED_CHAT_ID);
        chatService.delete(MOCKED_CHAT_ID);
        Optional<Chat> chat = chatRepository.findById(MOCKED_CHAT_ID);
        assertTrue(chat.isEmpty());
    }
}
