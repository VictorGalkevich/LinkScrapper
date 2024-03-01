package edu.java.bot.client;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class Client {
    protected final WebClient client;

    public Client(String baseUrl) {
        client = WebClient.create(baseUrl);
    }
}
