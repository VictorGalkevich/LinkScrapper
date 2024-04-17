package edu.java.bot.processor;

import edu.java.bot.command.StartCommand;
import edu.java.bot.exception.ApiResponseException;
import edu.java.dto.response.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartMessageProcessorTest extends ProcessorTest {
    @Override
    void init() {
        super.init();
        command = new StartCommand(config);
        processor = new StartMessageProcessor(
                scrapperClient);
    }

    @Test
    public void testCommandHandleNonRegisteredPositive() {
        final String expected = "Welcome, %s, use /help to see the help page".formatted(MOCKED_USERNAME);
        Mockito.doReturn(Mono.just(ResponseEntity.ok().build()))
                .when(scrapperClient)
                .register(MOCKED_USER_ID);
        String text = processor.process(command, update).text();
        assertEquals(expected, text);
    }

    @Test
    public void testCommandHandleRegisteredPositive() {
        final String expected = "You are already registered";
        ApiErrorResponse mock = Mockito.mock(ApiErrorResponse.class);
        Mockito.doReturn("You are already registered")
                .when(mock).description();
        Mockito.doReturn(Mono.error(new ApiResponseException(mock)))
                .when(scrapperClient)
                .register(MOCKED_USER_ID);
        String text = processor.process(command, update).text();
        assertEquals(expected, text);
    }
}
