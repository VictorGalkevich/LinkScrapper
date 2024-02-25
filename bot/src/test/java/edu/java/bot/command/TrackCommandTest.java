package edu.java.bot.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackCommandTest extends CommandTest {
    private TrackCommand command;

    @Override
    void init() {
        super.init();
        command = new TrackCommand(config);
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/track", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("start tracking a link", command.description());
    }
}
