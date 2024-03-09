package edu.java.bot.tgbot.model;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotUpdateTest {
    private static BotUpdate update;
    private static final Long MOCKED_USER_ID = 123L;
    private static final String MOCKED_USERNAME = "Petya";
    private static final String MOCKED_TEXT = "Test";

    @BeforeAll
    static void init() {
        Update source = Mockito.spy(new Update());
        Chat chat = Mockito.spy(new Chat());
        Message message = Mockito.spy(new Message());
        update = Mockito.spy(new BotUpdate(source));
        Mockito.doReturn(chat).when(message).chat();
        Mockito.doReturn(message).when(source).message();
        Mockito.doReturn(MOCKED_USER_ID).when(chat).id();
        Mockito.doReturn(MOCKED_TEXT).when(message).text();
        Mockito.doReturn(MOCKED_USERNAME).when(chat).username();
    }

    @Test
    void testIdMethod() {
        assertEquals(MOCKED_USER_ID, update.id());
    }

    @Test
    void testUsernameMethod() {
        assertEquals(MOCKED_USERNAME, update.username());
    }

    @Test
    void testTextMethod() {
        assertEquals(MOCKED_TEXT, update.text());
    }
}
