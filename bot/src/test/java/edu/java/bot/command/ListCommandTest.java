package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest extends CommandTest {
    private ListCommand command;

    @Override
    void init() {
        super.init();
        command = new ListCommand(config);
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/list", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("list tracked links", command.description());
    }

}
