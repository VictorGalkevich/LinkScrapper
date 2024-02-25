package edu.java.bot.configuration;

import edu.java.bot.formatter.Formatter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TemplateMessageConfig {
    private final Formatter formatter;
    private static final String LIST_ELEMENT = " - %s\n";

    @Bean
    public String help() {
        return formatter.bold("%s") + LIST_ELEMENT;
    }

    @Bean
    public String list() {
        return formatter.bold("%s)") + LIST_ELEMENT;
    }
}
