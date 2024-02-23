package edu.java.bot.processor;

import edu.java.bot.command.HelpCommand;
import edu.java.bot.formatter.Formatter;
import edu.java.bot.formatter.HTMLFormatter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpMessageProcessorTest extends ProcessorTest {
    private Formatter formatter;

    @Override
    void init() {
        super.init();
        command = new HelpCommand(config);
        formatter = new HTMLFormatter();
        processor = new HelpMessageProcessor(
                List.of(
                        command
                ), formatter.bold("%s") + " - %s\n");
    }

    @Test
    public void testCommandHandlePositive() {
        final String expected =
                "Available commands: \n" + formatter.bold("/help") + " - show available commands\n";
        Object text = processor.process(command, update).text();
        assertEquals(expected, text);
    }
}
