package edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class Client {
    private final WebClient client;

    public Client(String baseUrl) {
        client = WebClient.create(baseUrl);
    }

    public WebClient client() {
        return client;
    }
}
