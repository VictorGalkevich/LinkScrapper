package edu.java.bot.command;

import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.processor.ListMessageProcessor;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest extends CommandTest {
    private ListCommand command;

    @Override
    void init() {
        super.init();
        command = new ListCommand(new ListMessageProcessor(userRepository));
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/list", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("show all tracked links", command.description());
    }

    @Test
    public void testCommandHandleNoLinksTrackedNegative() {
        final String expected = "You are not tracking any links yet";
        Object text = command.handle(update).getParameters().get("text");
        assertEquals(expected, text);
    }

    @Test
    public void testCommandHandleHasLinksPositive() {
        final String expected = "Tracked links: \n<b>1)</b> - yandex.ru\n";
        Link link = Link.builder()
            .uri("yandex.ru")
            .build();
        Mockito.doReturn(Optional.of(new User(1L, List.of(link)))).when(userRepository)
            .findById(Mockito.any(Long.class));
        Object text = command.handle(update).getParameters().get("text");
        assertEquals(expected, text);
    }
}
