package edu.java.bot.processor;

import edu.java.bot.command.UntrackCommand;
import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.util.LinkUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UntrackMessageProcessorTest extends ProcessorTest {
    private User mockUser;
    @Override
    void init() {
        super.init();
        command = new UntrackCommand(config);
        processor = new UntrackMessageProcessor(
                userRepository);
        mockUser = new User(MOCKED_USER_ID, new ArrayList<>());
    }
    @Test
    public void testCommandHandleOneArgumentNegative() {
        final String expected = "/untrack syntax might be [/untrack <link>]";
        Mockito.doReturn("/untrack").when(update).text();
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleWrongLinkNegative() {
        final String expected = "Provided link is invalid";
        Mockito.doReturn("/untrack yandex").when(update).text();
        Mockito.doReturn(Optional.of(mockUser)).when(userRepository).findById(MOCKED_USER_ID);
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleIsNotTrackedNegative() {
        final String expected = "Link is not being tracked";
        Mockito.doReturn(Optional.of(mockUser)).when(userRepository)
                .findById(Mockito.any(Long.class));
        Mockito.doReturn("/untrack http://yandex.ru").when(update).text();
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleRemovedPositive() {
        final String expected = "Link was removed from tracking list!";
        Link link = LinkUtil.parse("http://google.com/");
        mockUser.getLinks().add(link);
        Mockito.doReturn(Optional.of(mockUser)).when(userRepository).findById(Mockito.any(Long.class));
        Mockito.doReturn("/untrack http://google.com").when(update).text();
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
        Assertions.assertThat(mockUser.getLinks()).hasSize(0);
    }
}
