package edu.java.bot.processor;

import edu.java.bot.command.TrackCommand;
import edu.java.bot.command.UnknownCommand;
import edu.java.bot.configuration.CommandConfig;
import edu.java.bot.tgbot.request.SendMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UnknownMessageProcessorTest extends ProcessorTest {

    @Override
    void init() {
        super.init();
        command = new UnknownCommand();
        processor = new UnknownMessageProcessor();
    }

    @Test
    public void testCommandHandlePositive() {
        final String expected =
                "Sorry, I can't proceed this type of message. \nAvailable commands: /help";
        Object text = processor.process(command, update).text();
        assertEquals(expected, text);
    }

    @Test
    @Override
    public void testCommandHandleNegative() {
        SendMessage process = processor.process(new TrackCommand(new CommandConfig()), update);
        assertNull(process);
    }
}
