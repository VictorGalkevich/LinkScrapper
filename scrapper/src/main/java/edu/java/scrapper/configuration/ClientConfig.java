package edu.java.scrapper.configuration;

import edu.java.backoff.filter.RetryFilter;
import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {
    private final LinkConfig config;
    private final ApplicationConfig applicationConfig;

    @Bean
    public GitHubClient github(RetryFilter gitHubFilter) {
        return new GitHubClient(config.getGithub(), gitHubFilter, applicationConfig.gitHubToken());
    }

    @Bean
    public StackOverflowClient stackoverflow(RetryFilter stackOverflowFilter) {
        return new StackOverflowClient(config.getStackoverflow(), stackOverflowFilter);
    }

    @Bean
    public BotClient bot(RetryFilter botFilter) {
        return new BotClient(config.getBot(), botFilter);
    }
}
