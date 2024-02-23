package edu.java.bot.processor;

import edu.java.bot.command.StartCommand;
import edu.java.bot.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartMessageProcessorTest extends ProcessorTest{
    @Override
    void init() {
        super.init();
        command = new StartCommand(config);
        processor = new StartMessageProcessor(
                userRepository);
    }
    @Test
    public void testCommandHandleNonRegisteredPositive() {
        final String expected = "Welcome, %s, use /help to see the help page".formatted(MOCKED_USERNAME);
        String text = processor.process(command, update).text();
        assertEquals(expected, text);
    }

    @Test
    public void testCommandHandleRegisteredPositive() {
        userRepository.save(new User(123L, null));
        final String expected = "Welcome, %s, use /help to see the help page".formatted(MOCKED_USERNAME);
        String text = processor.process(command, update).text();
        assertEquals(expected, text);
    }
}
