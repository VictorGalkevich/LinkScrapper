package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper.dto.GitHubResponseDto;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubClientTest extends ClientTest {
    private static final String SITE = ".github";
    private static final String URL = "https://docs.github.com/rest/repos/repos#get-a-repository";
    private static final String MOCKED_PATH = "username/repository";
    private static final String UPDATED_AT = "1111-11-11T11:11:11Z";
    private static final OffsetDateTime repositoryUpdatedAt =
        OffsetDateTime.of(1111, 11, 11, 11, 11, 11, 0, ZoneOffset.of("Z"));

    @Autowired
    private GitHubClient client;

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        configureProperties(registry, SITE);
    }

    @Test
    public void assertThatExistsRepositoryGetUserRepositoryReturnedOk() {
        WIRE_MOCK_SERVER.stubFor(WireMock.get("/repos/" + MOCKED_PATH)
            .willReturn(WireMock.ok()
                .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                        "full_name": "%s",
                        "updated_at": "%s"
                    }
                    """.formatted(MOCKED_PATH, UPDATED_AT))));

        GitHubResponseDto response = client.getRepositoryInfo(MOCKED_PATH).block();

        Objects.requireNonNull(response);
        assertEquals(MOCKED_PATH, response.fullName());
        assertEquals(repositoryUpdatedAt, response.updatedAt());
    }

    @Test
    public void assertThatNonExistsOrPrivateRepositoryGetUserRepositoryReturnedNotFound() {
        WIRE_MOCK_SERVER.stubFor(WireMock.get("/repos/" + MOCKED_PATH)
            .willReturn(WireMock.notFound()
                .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                        "message": "Not Found",
                        "documentation_url": "%s"
                    }
                    """.formatted(URL))));

        assertThrows(WebClientResponseException.class, () -> client.getRepositoryInfo(MOCKED_PATH).block());
    }
}
