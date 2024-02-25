package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest extends CommandTest {
    private HelpCommand command;

    @Override
    void init() {
        super.init();
        command = new HelpCommand(config);
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/help", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("show available commands", command.description());
    }
}
