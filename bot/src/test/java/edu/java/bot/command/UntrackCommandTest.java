package edu.java.bot.command;

import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.processor.UntrackMessageProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UntrackCommandTest extends CommandTest {
    private UntrackCommand command;

    @Override
    void init() {
        super.init();
        command = new UntrackCommand(new UntrackMessageProcessor(userRepository));
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/untrack", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("stop tracking a link", command.description());
    }

    @Test
    public void testCommandHandleOneArgumentNegative() {
        final String expected = "/untrack syntax might be [/untrack <link>]";
        Mockito.doReturn("/untrack").when(message).text();
        Object actual = command.handle(update).getParameters().get("text");
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleWrongLinkNegative() {
        final String expected = "Provided link is invalid";
        Mockito.doReturn("/track yandex").when(message).text();
        Object actual = command.handle(update).getParameters().get("text");
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleIsNotTrackedNegative() {
        final String expected = "Link is not being tracked";
        Mockito.doReturn(Optional.of(new User(1L, new ArrayList<>()))).when(userRepository)
            .findById(Mockito.any(Long.class));
        Mockito.doReturn("/track yandex.ru").when(message).text();
        Object actual = command.handle(update).getParameters().get("text");
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleRemovedPositive() {
        final String expected = "Link was removed from tracking list!";
        Link link = Link.builder()
            .uri("http://google.com/")
            .build();
        List<Link> list = new ArrayList<>();
        list.add(link);
        Mockito.doReturn(Optional.of(new User(1L, list))).when(userRepository).findById(Mockito.any(Long.class));
        Mockito.doReturn("/track http://google.com").when(message).text();
        Object actual = command.handle(update).getParameters().get("text");
        assertEquals(expected, actual);
    }
}
