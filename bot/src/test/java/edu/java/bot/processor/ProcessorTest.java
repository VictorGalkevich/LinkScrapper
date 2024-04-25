package edu.java.bot.processor;

import edu.java.backoff.filter.RetryFilter;
import edu.java.backoff.type.ConstantBackoff;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.Command;
import edu.java.bot.command.UnknownCommand;
import edu.java.bot.configuration.CommandConfig;
import edu.java.bot.configuration.ScrapperConfig;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class ProcessorTest {
    protected BotUpdate update;
    protected ScrapperClient scrapperClient;
    protected Command command;
    protected UserMessageProcessor processor;
    protected CommandConfig config;
    protected static final Long MOCKED_USER_ID = 123L;
    protected static final Long MOCKED_LINK_ID = 123L;
    protected static final String MOCKED_LINK = "https://yandex.ru";
    protected static final String MOCKED_USERNAME = "Petya";

    @BeforeEach
    void init() {
        update = Mockito.spy(new BotUpdate(null));
        scrapperClient = Mockito.spy(new ScrapperClient(
            "http://localhost:8080",
            new ScrapperConfig(),
            new RetryFilter(
                new ConstantBackoff(null),
                Set.of(),
                1
            )
        ));
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
