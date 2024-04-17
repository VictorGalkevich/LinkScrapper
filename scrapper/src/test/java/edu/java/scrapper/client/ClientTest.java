package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.test.context.DynamicPropertyRegistry;

public class ClientTest {
    protected static final String PREFIX = "app.client";

    @RegisterExtension
    protected static final WireMockExtension WIRE_MOCK_SERVER = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    protected static void configureProperties(DynamicPropertyRegistry registry, String site) {
        registry.add(PREFIX + site, WIRE_MOCK_SERVER::baseUrl);
    }
}
