package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartCommandTest extends CommandTest {
    private StartCommand command;

    @Override
    void init() {
        super.init();
        command = new StartCommand(config);
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/start", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("start the bot", command.description());
    }
}
