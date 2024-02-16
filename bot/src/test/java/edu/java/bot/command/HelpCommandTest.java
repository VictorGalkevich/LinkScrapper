package edu.java.bot.command;

import edu.java.bot.processor.HelpMessageProcessor;
import edu.java.bot.processor.StartMessageProcessor;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest extends CommandTest {
    private HelpCommand command;

    @Override
    void init() {
        super.init();
        List<Command> cmds = new ArrayList<>();
        Command startcmd = new StartCommand(new StartMessageProcessor(userRepository));
        cmds.add(startcmd);
        command = new HelpCommand(cmds, new HelpMessageProcessor());
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/help", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("show available commands", command.description());
    }

    @Test
    public void testCommandHandlePositive() {
        final String expected =
            "Available commands: \n<b>/start</b> - start the bot\n<b>/help</b> - show available commands\n";
        Object text = command.handle(update).getParameters().get("text");
        assertEquals(expected, text);
    }
}
