package edu.java.scrapper.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.exception.message", ignoreUnknownFields = false)
@Getter
@Setter
public class ExceptionConfig {
    private String alreadyRegistered = "You are already registered";
    private String isNotRegistered = "You are not registered";
    private String alreadyTracked = "Link is already being tracked";
    private String isNotTracked = "Link is not being tracked";
}
