package edu.java.bot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

@ConfigurationProperties(prefix = "app.scrapper")
@Getter
@Setter
public class ScrapperConfig {
    private String route = "http://localhost:8080";
    private String header = "Tg-Chat-Id";
    private String links = "/links";
    private String chat = "/tg-chat/{id}";
}
