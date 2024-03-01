package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;


import edu.java.scrapper.dto.StackOverflowResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StackOverflowClientTest extends ClientTest {
    private static final String SITE = ".stackoverflow";
    private static final String MOCKED_TITLE = "test_title";
    private static final Long MOCKED_QUESTION_ID = 123L;
    private static final Long EPOCH = 1590400952L;
    private static final OffsetDateTime MOCKED_UPDATE_TIME = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1590400952), ZoneId.of("Z"));

    @Autowired
    private StackOverflowClient client;

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        configureProperties(registry, SITE);
    }

    @Test
    public void assertThatExistsQuestionReturnedOk() {
        WIRE_MOCK_SERVER.stubFor(WireMock.get("/questions/" + MOCKED_QUESTION_ID + "?site=stackoverflow")
                .willReturn(WireMock.ok()
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                    {
                        "items": [
                            {
                                "last_activity_date": %d,
                                "title": "%s"
                            }
                        ]
                    }
                    """.formatted(EPOCH, MOCKED_TITLE))));

        StackOverflowResponseDto response = client.getQuestionInfo(MOCKED_QUESTION_ID).block();

        assertNotNull(response);
        assertEquals(MOCKED_TITLE, response.items().getFirst().title());
        assertEquals(MOCKED_UPDATE_TIME, response.items().getFirst().lastActivityDate());
    }

    @Test
    public void assertThatNonExistsQuestionReturnedOkAndEmptyList() {
        WIRE_MOCK_SERVER.stubFor(WireMock.get("/questions/" + MOCKED_QUESTION_ID + "?site=stackoverflow")
                .willReturn(WireMock.ok()
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                    {
                        "items": []
                    }
                    """)));

        StackOverflowResponseDto response = client.getQuestionInfo(MOCKED_QUESTION_ID).block();

        assertNotNull(response);
        assertTrue(response.items().isEmpty());
    }
}
