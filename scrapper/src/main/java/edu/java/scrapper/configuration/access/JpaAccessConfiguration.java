package edu.java.scrapper.configuration.access;

import edu.java.scrapper.repository.jpa.JpaChatRepository;
import edu.java.scrapper.repository.jpa.JpaGitHubRepository;
import edu.java.scrapper.repository.jpa.JpaLinkRepository;
import edu.java.scrapper.repository.jpa.JpaStackOverflowRepository;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.GitHubService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.StackOverflowService;
import edu.java.scrapper.service.jpa.JpaChatService;
import edu.java.scrapper.service.jpa.JpaGitHubService;
import edu.java.scrapper.service.jpa.JpaLinkService;
import edu.java.scrapper.service.jpa.JpaStackOverflowService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static edu.java.scrapper.configuration.access.AccessType.JPA;

@Configuration
@ConditionalOnProperty(
    prefix = "app",
    name = "database-access-type",
    havingValue = JPA
)
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
        JpaLinkRepository jpaLinkRepository,
        JpaChatRepository jpaChatRepository
    ) {
        return new JpaLinkService(jpaLinkRepository, jpaChatRepository);
    }

    @Bean
    public ChatService telegramChatService(JpaChatRepository jpaTelegramChatRepository) {
        return new JpaChatService(jpaTelegramChatRepository);
    }

    @Bean
    public GitHubService gitHubLinkService(JpaGitHubRepository jpaGitHubLinkRepository) {
        return new JpaGitHubService(jpaGitHubLinkRepository);
    }

    @Bean
    public StackOverflowService stackOverflowLinkService(
        JpaStackOverflowRepository jpaStackOverflowLinkRepository
    ) {
        return new JpaStackOverflowService(jpaStackOverflowLinkRepository);
    }

}
