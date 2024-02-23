package edu.java.bot.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UntrackCommandTest extends CommandTest {
    private UntrackCommand command;

    @Override
    void init() {
        super.init();
        command = new UntrackCommand(config);
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/untrack", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("stop tracking a link", command.description());
    }

}
