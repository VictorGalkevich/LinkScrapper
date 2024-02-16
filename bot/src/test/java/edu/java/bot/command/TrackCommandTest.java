package edu.java.bot.command;

import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.processor.TrackMessageProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackCommandTest extends CommandTest {
    private TrackCommand command;
    @Override
    void init() {
        super.init();
        command = new TrackCommand(new TrackMessageProcessor(userRepository));
    }

    @Test
    void testCommandNamePositive() {
        assertEquals("/track", command.command());
    }

    @Test
    public void testCommandDescriptionPositive() {
        assertEquals("start tracking a link", command.description());
    }

    @Test
    public void testCommandHandleOneArgumentNegative() {
        final String expected = "/track syntax might be [/track <link>]";
        Mockito.doReturn("/track").when(message).text();
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
    public void testCommandHandleAlreadyTrackedNegative() {
        final String expected = "Link is already being tracked";
        Link link = Link.builder()
            .uri("yandex.ru/")
            .build();
        List<Link> list = new ArrayList<>();
        list.add(link);
        Mockito.doReturn(Optional.of(new User(1L, list))).when(userRepository).findById(Mockito.any(Long.class));
        Mockito.doReturn("/track yandex.ru").when(message).text();
        Object actual = command.handle(update).getParameters().get("text");
        assertEquals(expected, actual);
    }
    @Test
    public void testCommandHandleAddedPositive() {
        final String expected = "Link was added to tracking list!";
        Mockito.doReturn(Optional.of(new User(1L, new ArrayList<>()))).when(userRepository).findById(Mockito.any(Long.class));
        Mockito.doReturn("/track http://google.com").when(message).text();
        Object actual = command.handle(update).getParameters().get("text");
        assertEquals(expected, actual);
    }
}
