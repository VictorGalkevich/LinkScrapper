package edu.java.scrapper.client;

import edu.java.backoff.filter.RetryFilter;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class Client {
    private final WebClient client;

    protected Client(String baseUrl, RetryFilter filter) {
        client = WebClient.builder()
            .baseUrl(baseUrl)
            .filter(filter)
            .build();
    }

    public WebClient client() {
        return client;
    }
}
