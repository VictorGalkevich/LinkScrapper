package edu.java.bot.configuration;

import edu.java.bot.formatter.Formatter;
import edu.java.bot.formatter.HTMLFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormatterConfig {
    @Bean
    public Formatter formatter() {
        return new HTMLFormatter();
    }
}
