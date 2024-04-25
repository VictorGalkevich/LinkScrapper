package edu.java.bot.configuration;

import edu.java.backoff.properties.RetryProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.retry")
@Getter
@Setter
public class RetryPropertiesConfig {
    private RetryProperties properties = new RetryProperties();
}
