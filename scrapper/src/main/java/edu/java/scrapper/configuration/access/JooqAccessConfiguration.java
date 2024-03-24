package edu.java.scrapper.configuration.access;

import edu.java.scrapper.repository.jooq.JooqChatRepository;
import edu.java.scrapper.repository.jooq.JooqGitHubRepository;
import edu.java.scrapper.repository.jooq.JooqLinkRepository;
import edu.java.scrapper.repository.jooq.JooqStackOverflowRepository;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.GitHubService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.StackOverflowService;
import edu.java.scrapper.service.jooq.JooqChatService;
import edu.java.scrapper.service.jooq.JooqGitHubService;
import edu.java.scrapper.service.jooq.JooqLinkService;
import edu.java.scrapper.service.jooq.JooqStackOverflowService;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
    prefix = "app",
    name = "database-access-type",
    havingValue = "jooq"
)public class JooqAccessConfiguration {
    @Bean
    public JooqLinkRepository jooqLinkRepository(DSLContext dslContext) {
        return new JooqLinkRepository(dslContext);
    }

    @Bean
    public LinkService linkService(JooqLinkRepository jooqLinkRepository) {
        return new JooqLinkService(jooqLinkRepository);
    }

    @Bean
    public JooqChatRepository jdbcTelegramChatRepository(DSLContext dslContext) {
        return new JooqChatRepository(dslContext);
    }

    @Bean
    public ChatService telegramChatService(JooqChatRepository jooqChatRepository) {
        return new JooqChatService(jooqChatRepository);
    }

    @Bean
    public JooqGitHubRepository jdbcGitHubLinkRepository(DSLContext dslContext) {
        return new JooqGitHubRepository(dslContext);
    }

    @Bean
    public GitHubService gitHubLinkService(JooqGitHubRepository jooqGitHubRepository) {
        return new JooqGitHubService(jooqGitHubRepository);
    }

    @Bean
    public JooqStackOverflowRepository jdbcStackOverflowLinkRepository(DSLContext dslContext) {
        return new JooqStackOverflowRepository(dslContext);
    }

    @Bean
    public StackOverflowService stackOverflowLinkService(
        JooqStackOverflowRepository jooqStackOverflowRepository
    ) {
        return new JooqStackOverflowService(jooqStackOverflowRepository);
    }
}
