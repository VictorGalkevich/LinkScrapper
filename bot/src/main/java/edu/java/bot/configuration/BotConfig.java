package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BotConfig {
    private final ApplicationConfig config;

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(config.telegramToken());
    }
}
