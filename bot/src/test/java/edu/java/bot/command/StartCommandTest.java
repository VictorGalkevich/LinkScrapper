package edu.java.bot.command;

import edu.java.bot.processor.StartMessageProcessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartCommandTest extends CommandTest {
    private StartCommand command;
    @Override
    void init() {
        super.init();
        command = new StartCommand(new StartMessageProcessor(userRepository));
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/start", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("start the bot", command.description());
    }

    @Test
    public void testCommandHandlePositive() {
        final String expected = "Welcome, null, use /help to see the help page";
        Object text = command.handle(update).getParameters().get("text");
        assertEquals(expected, text);
    }
}
