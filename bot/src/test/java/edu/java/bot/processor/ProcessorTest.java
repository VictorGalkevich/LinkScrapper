package edu.java.bot.processor;

import edu.java.bot.command.Command;
import edu.java.bot.command.UnknownCommand;
import edu.java.bot.configuration.CommandConfig;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class ProcessorTest {
    protected BotUpdate update;
    protected UserRepository userRepository;
    protected Command command;
    protected UserMessageProcessor processor;
    protected CommandConfig config;
    protected static final Long MOCKED_USER_ID = 123L;
    protected static final String MOCKED_USERNAME = "Petya";

    @BeforeEach
    void init() {
        update = Mockito.spy(new BotUpdate(null));
        userRepository = Mockito.spy(new UserRepository());
        config = new CommandConfig();
        Mockito.doReturn(MOCKED_USER_ID).when(update).id();
        Mockito.doReturn(MOCKED_USERNAME).when(update).username();
    }

    @Test
    public void testCommandHandleNegative() {
        SendMessage process = processor.process(new UnknownCommand(), update);
        assertNull(process);
    }
}
