package edu.java.bot.processor;

import edu.java.bot.command.TrackCommand;
import edu.java.bot.exception.ApiResponseException;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.dto.response.LinkResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackMessageProcessorTest extends ProcessorTest {

    @Override
    void init() {
        super.init();
        command = new TrackCommand(config);
        processor = new TrackMessageProcessor(
                scrapperClient);
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
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleAlreadyTrackedNegative() {
        final String expected = "Link is already being tracked";
        Mockito.doReturn("/track " + MOCKED_LINK).when(update).text();
        ApiErrorResponse mock = Mockito.mock(ApiErrorResponse.class);
        Mockito.doReturn("Link is already being tracked").when(mock).description();
        Mockito.doReturn(Mono.error(new ApiResponseException(mock)))
                .when(scrapperClient).addLink(Mockito.any(), Mockito.any());
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }

    @Test
    public void testCommandHandleAddedPositive() {
        final String expected = "Link was added to tracking list!";
        Mockito.doReturn("/track " + MOCKED_LINK).when(update).text();
        AddLinkRequest request = Mockito.spy(new AddLinkRequest(URI.create(MOCKED_LINK)));
        Mockito.doReturn(Mono.just(ResponseEntity.ok().body(new LinkResponse(MOCKED_LINK_ID, request.link()))))
                .when(scrapperClient).addLink(Mockito.any(), Mockito.any());
        String actual = processor.process(command, update).text();
        assertEquals(expected, actual);
    }
}
