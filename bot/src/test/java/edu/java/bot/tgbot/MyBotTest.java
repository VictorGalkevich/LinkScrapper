package edu.java.bot.tgbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.UnknownCommand;
import edu.java.bot.configuration.CommandConfig;
import edu.java.bot.processor.StartMessageProcessor;
import edu.java.bot.processor.UnknownMessageProcessor;
import edu.java.bot.processor.UserMessageProcessor;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import java.lang.reflect.Method;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyBotTest {
    private MyBot mybot;
    private BotUpdate update;

    @BeforeEach
    void init() {
        TelegramBot tgbot = Mockito.spy(new TelegramBot("token"));
        CommandConfig config = new CommandConfig();
        List<Command> cmds = List.of(new StartCommand(config));
        List<UserMessageProcessor> procs = List.of(new StartMessageProcessor(null), new UnknownMessageProcessor());
        mybot = Mockito.spy(new MyBot(cmds, procs, tgbot, null));
        update = Mockito.spy(new BotUpdate(null));
    }

    @Test
    @SneakyThrows
    void testUnknownCommandTypeNegative() {
        Mockito.doReturn("/unknown").when(update).text();
        Method retrieveCommand = MyBot.class.getDeclaredMethod("retrieveCommand", BotUpdate.class);
        retrieveCommand.setAccessible(true);
        Object invoke = retrieveCommand.invoke(mybot, update);
        assertTrue(invoke instanceof UnknownCommand);
    }

    @Test
    @SneakyThrows
    void testExistingCommandTypePositive() {
        Mockito.doReturn("/start").when(update).text();
        Method retrieveCommand = MyBot.class.getDeclaredMethod("retrieveCommand", BotUpdate.class);
        retrieveCommand.setAccessible(true);
        Object invoke = retrieveCommand.invoke(mybot, update);
        assertTrue(invoke instanceof StartCommand);
    }

    @Test
    @SneakyThrows
    void testUnknownMessageTypePositive() {
        Mockito.doReturn("test").when(update).text();
        Mockito.doReturn(1L).when(update).id();
        Method preProcess = MyBot.class.getDeclaredMethod("processUpdate", BotUpdate.class);
        preProcess.setAccessible(true);
        SendMessage invoke = (SendMessage) preProcess.invoke(mybot, update);
        String expected = "Sorry, I can't proceed this type of message. \nAvailable commands: /help";
        String actual = invoke.text();
        assertEquals(expected, actual);
    }

    @Test
    @SneakyThrows
    void testSetMenuCommands() {
        Method setUpMenuCommands = MyBot.class.getDeclaredMethod("setUpMenuCommands");
        setUpMenuCommands.setAccessible(true);
        SetMyCommands invoke = (SetMyCommands) setUpMenuCommands.invoke(mybot);
        BotCommand actual = ((BotCommand[]) invoke.getParameters().get("commands"))[0];
        assertEquals("/start", actual.command());
    }
}
