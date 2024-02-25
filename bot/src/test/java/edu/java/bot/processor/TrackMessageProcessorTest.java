package edu.java.bot.processor;

import edu.java.bot.command.TrackCommand;
import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.util.LinkUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackMessageProcessorTest extends ProcessorTest {
    private User mockUser;
    @Override
    void init() {
        super.init();
        command = new TrackCommand(config);
        processor = new TrackMessageProcessor(
                userRepository);
        mockUser = new User(MOCKED_USER_ID, new ArrayList<>());
    }
    @Test
    public void testCommandHandleOneArgumentNegative() {
        final String expected = "/track syntax might be [/track <link>]";
        Mockito.doReturn("/track").when(update).text();
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleWrongLinkNegative() {
        final String expected = "Provided link is invalid";
        Mockito.doReturn("/track yandex").when(update).text();
        Mockito.doReturn(Optional.of(mockUser)).when(userRepository).findById(MOCKED_USER_ID);
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleAlreadyTrackedNegative() {
        final String expected = "Link is already being tracked";
        Link link = LinkUtil.parse("http://yandex.ru");
        mockUser.getLinks().add(link);
        Mockito.doReturn(Optional.of(mockUser)).when(userRepository).findById(Mockito.any(Long.class));
        Mockito.doReturn("/track http://yandex.ru").when(update).text();
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleAddedPositive() {
        final String expected = "Link was added to tracking list!";
        Mockito.doReturn(Optional.of(mockUser)).when(userRepository).findById(Mockito.any(Long.class));
        Mockito.doReturn("/track http://google.com").when(update).text();
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
        assertThat(mockUser.getLinks()).hasSize(1);
    }
}
