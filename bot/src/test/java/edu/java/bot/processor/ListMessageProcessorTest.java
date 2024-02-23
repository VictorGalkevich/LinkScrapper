package edu.java.bot.processor;

import edu.java.bot.command.ListCommand;
import edu.java.bot.entity.Link;
import edu.java.bot.entity.User;
import edu.java.bot.formatter.Formatter;
import edu.java.bot.formatter.HTMLFormatter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListMessageProcessorTest extends ProcessorTest {
    private Formatter formatter;

    @Override
    void init() {
        super.init();
        command = new ListCommand(config);
        formatter = new HTMLFormatter();
        processor = new ListMessageProcessor(
                userRepository,
                formatter.bold("%s)") + " - %s\n");
    }
    @Test
    public void testCommandHandleNoLinksTrackedNegative() {
        final String expected = "You are not tracking any links yet";
        String text = processor.process(command, update).text();
        assertEquals(expected, text);
    }

    @Test
    public void testCommandHandleHasLinksPositive() {
        final String expected = "Tracked links: \n" + formatter.bold("1)") + " - yandex.ru\n";
        Link link = Link.builder()
                .uri("yandex.ru")
                .build();
        Mockito.doReturn(Optional.of(new User(1L, List.of(link)))).when(userRepository)
                .findById(Mockito.any(Long.class));
        String text = processor.process(command, update).text();
        assertEquals(expected, text);
    }
}
