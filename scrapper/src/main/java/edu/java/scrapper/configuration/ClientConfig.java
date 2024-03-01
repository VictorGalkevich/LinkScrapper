package edu.java.scrapper.configuration;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {
    private final LinkConfig config;

    @Bean
    public GitHubClient github() {
        return new GitHubClient(config.getGithub());
    }

    @Bean
    public StackOverflowClient stackoverflow() {
        return new StackOverflowClient(config.getStackoverflow());
    }
}
