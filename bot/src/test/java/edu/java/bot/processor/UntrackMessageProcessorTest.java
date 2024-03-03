package edu.java.bot.processor;

import edu.java.bot.command.UntrackCommand;
import edu.java.bot.exception.ApiResponseException;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.dto.response.LinkResponse;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UntrackMessageProcessorTest extends ProcessorTest {

    @Override
    void init() {
        super.init();
        command = new UntrackCommand(config);
        processor = new UntrackMessageProcessor(
            scrapperClient);
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
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleIsNotTrackedNegative() {
        final String expected = "Link is not being tracked";
        Mockito.doReturn("/untrack " + MOCKED_LINK).when(update).text();

        ApiErrorResponse mock = Mockito.mock(ApiErrorResponse.class);
        Mockito.doReturn("Link is not being tracked").when(mock).description();
        Mockito.doReturn(Mono.error(new ApiResponseException(mock)))
            .when(scrapperClient).remove(Mockito.any(), Mockito.any());

        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleRemovedPositive() {
        final String expected = "Link was removed from tracking list!";
        Mockito.doReturn("/untrack " + MOCKED_LINK).when(update).text();

        URI uri = Mockito.spy(URI.create(MOCKED_LINK));

        Mockito.doReturn(Mono.just(ResponseEntity.ok().body(new LinkResponse(MOCKED_LINK_ID, uri))))
            .when(scrapperClient).remove(Mockito.any(), Mockito.any());

        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }
}
