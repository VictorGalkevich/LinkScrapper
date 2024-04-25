package edu.java.scrapper.configuration;

import edu.java.backoff.properties.RetryProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.retry")
@Getter
@Setter
public class RetryPropertiesConfig {
        private RetryProperties bot;
        private RetryProperties github;
        private RetryProperties stackoverflow;

    {
            bot = new RetryProperties();
            github = new RetryProperties();
            stackoverflow = new RetryProperties();
    }
}
