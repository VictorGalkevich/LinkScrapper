package edu.java.scrapper.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.client", ignoreUnknownFields = false)
@Getter
@Setter
public class LinkConfig {
    private String github = "https://api.github.com";
    private String stackoverflow = "https://api.stackexchange.com/2.3";
}
