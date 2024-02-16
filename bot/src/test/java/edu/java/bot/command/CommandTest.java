package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public abstract class CommandTest {
    protected Update update;
    protected Message message;
    protected UserRepository userRepository;
    protected Long chatId;

    @BeforeEach
    void init() {
        update = Mockito.spy(new Update());
        message = Mockito.spy(new Message());
        Chat chat = Mockito.spy(new Chat());
        userRepository = Mockito.spy(new UserRepository());
        chatId = 123L;

        Mockito.doReturn(message).when(update).message();
        Mockito.doReturn(chat).when(message).chat();
        Mockito.doReturn(chatId).when(chat).id();
    }
}
