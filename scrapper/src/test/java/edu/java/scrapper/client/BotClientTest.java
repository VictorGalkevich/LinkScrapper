package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.dto.request.LinkUpdate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import java.net.URI;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BotClientTest extends ClientTest {
    @Autowired
    private BotClient client;
    private final static String DESCRIPTION = "updates";
    private final static String UPDATES = "/updates";
    private final static Long MOCKED_UPDATE_ID = 123L;
    private final static Long MOCKED_CHAT_ID = 123L;
    private final static URI MOCKED_URI = URI.create("https://yandex.ru");
    private static final String SITE = ".bot";

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        configureProperties(registry, SITE);
    }

    @Test
    void testSendUpdatesPositive() {
        final LinkUpdate update = new LinkUpdate(
            MOCKED_UPDATE_ID, MOCKED_URI,
            DESCRIPTION, List.of(MOCKED_CHAT_ID));
        WIRE_MOCK_SERVER.stubFor(WireMock.post(UPDATES)
            .withRequestBody(WireMock.equalToJson("""
                {
                    "id": %d,
                    "url": "%s",
                      "description": "%s",
                      "tgChatIds": [
                        %d
                      ]
                }
                """.formatted(MOCKED_UPDATE_ID, MOCKED_URI,
                DESCRIPTION, MOCKED_CHAT_ID)))
            .willReturn(WireMock.ok()));
        ResponseEntity<Void> resp = client.sendUpdate(update).block();

        Assertions.assertNotNull(resp);
        Assertions.assertEquals(HttpStatus.OK, resp.getStatusCode());
    }
}
