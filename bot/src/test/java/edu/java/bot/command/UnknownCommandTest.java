package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnknownCommandTest extends CommandTest {
    private UnknownCommand command;

    @Override
    void init() {
        super.init();
        command = new UnknownCommand();
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("", command.description());
    }
}
