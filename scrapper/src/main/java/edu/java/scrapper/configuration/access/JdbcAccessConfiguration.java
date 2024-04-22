package edu.java.scrapper.configuration.access;

import edu.java.scrapper.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.repository.jdbc.JdbcGitHubRepository;
import edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repository.jdbc.JdbcStackOverflowRepository;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.GitHubService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.StackOverflowService;
import edu.java.scrapper.service.jdbc.JdbcChatService;
import edu.java.scrapper.service.jdbc.JdbcGitHubService;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import edu.java.scrapper.service.jdbc.JdbcStackOverflowService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;
import static edu.java.scrapper.configuration.access.AccessType.JDBC;

@Configuration
@ConditionalOnProperty(
    prefix = "app",
    name = "database-access-type",
    havingValue = JDBC
                       )
public class JdbcAccessConfiguration {
    @Bean
    public JdbcLinkRepository jdbcLinkRepository(JdbcClient jdbcClient) {
        return new JdbcLinkRepository(jdbcClient);
    }

    @Bean
    public LinkService linkService(JdbcLinkRepository jdbcLinkRepository) {
        return new JdbcLinkService(jdbcLinkRepository);
    }

    @Bean
    public JdbcChatRepository jdbcTelegramChatRepository(JdbcClient jdbcClient) {
        return new JdbcChatRepository(jdbcClient);
    }

    @Bean
    public ChatService telegramChatService(JdbcChatRepository jdbcChatRepository) {
        return new JdbcChatService(jdbcChatRepository);
    }

    @Bean
    public JdbcGitHubRepository jdbcGitHubLinkRepository(JdbcClient jdbcClient) {
        return new JdbcGitHubRepository(jdbcClient);
    }

    @Bean
    public GitHubService gitHubLinkService(JdbcGitHubRepository jdbcGitHubRepository) {
        return new JdbcGitHubService(jdbcGitHubRepository);
    }

    @Bean
    public JdbcStackOverflowRepository jdbcStackOverflowLinkRepository(JdbcClient jdbcClient) {
        return new JdbcStackOverflowRepository(jdbcClient);
    }

    @Bean
    public StackOverflowService stackOverflowLinkService(
        JdbcStackOverflowRepository jdbcStackOverflowLinkRepository
    ) {
        return new JdbcStackOverflowService(jdbcStackOverflowLinkRepository);
    }
}
