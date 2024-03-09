package edu.java.bot.processor;

import edu.java.bot.command.ListCommand;
import edu.java.bot.formatter.Formatter;
import edu.java.bot.formatter.HTMLFormatter;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.ResponseEntity.ok;

public class ListMessageProcessorTest extends ProcessorTest {
    private Formatter formatter;

    @Override
    void init() {
        super.init();
        command = new ListCommand(config);
        formatter = new HTMLFormatter();
        processor = new ListMessageProcessor(
            formatter.bold("%s)") + " - %s\n",
            formatter,
            scrapperClient
        );
    }

    @Test
    public void testCommandHandleNoLinksTrackedNegative() {
        final String expected = "You are not tracking any links yet";
        Mockito.doReturn(Mono.just(ok()
                .body(new ListLinksResponse(List.of(), 0))))
            .when(scrapperClient)
            .getAllLinks(MOCKED_USER_ID);
        String text = processor.process(command, update).text();
        assertEquals(expected, text);
    }

    @Test
    public void testCommandHandleHasLinksPositive() {
        final String expected = "Tracked links: \n" + formatter.bold("1)") + " - %s\n"
            .formatted(MOCKED_LINK);
        Mockito.doReturn(Mono.just(ok()
                .body(new ListLinksResponse(List.of(
                    new LinkResponse(MOCKED_LINK_ID, URI.create(MOCKED_LINK))
                ), 0))))
            .when(scrapperClient)
            .getAllLinks(MOCKED_USER_ID);
        String text = processor.process(command, update).text();
        assertEquals(expected, text);
    }
}
