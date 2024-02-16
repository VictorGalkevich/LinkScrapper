package edu.java.bot.tgbot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.command.StartCommand;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.processor.StartMessageProcessor;
import edu.java.bot.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyBotTest {
    private MyBot mybot;
    private Update update;
    private Message message;

    @BeforeEach
    void init() {
        ApplicationConfig conf = Mockito.spy(new ApplicationConfig("asd"));
        List<Command> cmds = List.of(new StartCommand(new StartMessageProcessor(new UserRepository())));
        mybot = Mockito.spy(new MyBot(conf, cmds));
        update = Mockito.spy(new Update());
        message = Mockito.spy(new Message());
        Mockito.doReturn(message).when(update).message();
        Chat chat = Mockito.spy(new Chat());
        Mockito.doReturn(chat).when(message).chat();
        Mockito.doReturn(123L).when(chat).id();
    }

    @Test
    @SneakyThrows
    void testUnknownCommandTypeNegative() {
        Mockito.doReturn("/unknown").when(message).text();
        Method retrieveCommand = MyBot.class.getDeclaredMethod("retrieveCommand", Update.class);
        retrieveCommand.setAccessible(true);
        Object invoke = retrieveCommand.invoke(mybot, update);
        assertNull(invoke);
    }

    @Test
    @SneakyThrows
    void testExistingCommandTypePositive() {
        Mockito.doReturn("/start").when(message).text();
        Method retrieveCommand = MyBot.class.getDeclaredMethod("retrieveCommand", Update.class);
        retrieveCommand.setAccessible(true);
        Object invoke = retrieveCommand.invoke(mybot, update);
        assertTrue(invoke instanceof StartCommand);
    }

    @Test
    @SneakyThrows
    void testUnknownMessageTypePositive() {
        Mockito.doReturn("test").when(message).text();
        Method preProcess = MyBot.class.getDeclaredMethod("preProcess", Update.class);
        preProcess.setAccessible(true);
        SendMessage invoke = (SendMessage) preProcess.invoke(mybot, update);
        String expected = "Sorry, I can't proceed this type of message. \nAvailable commands: /help";
        String actual = (String) invoke.getParameters().get("text");
        assertEquals(expected, actual);
    }
}
