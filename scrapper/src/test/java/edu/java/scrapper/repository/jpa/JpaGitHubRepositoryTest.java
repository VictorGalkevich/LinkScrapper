package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.ScrapperIT;
import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import java.net.URI;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ScrapperIT
public class JpaGitHubRepositoryTest extends JpaRepositoryTest {
    @Autowired
    private JpaGitHubRepository gitHubLinkRepository;
    private static final Long MOCK_ID = 123L;
    private static final Long MOCK_FORKS_COUNT = 123L;
    private static final Link MOCK_LINK = Link.fromUrl(URI.create("https://yandex.ru"));
    private static final GitHubLink MOCK_GITHUB_LINK;
    private static final String MOCK_BRANCH = "master";

    static {
        MOCK_GITHUB_LINK = new GitHubLink()
            .setDefaultBranch(MOCK_BRANCH)
            .setForksCount(MOCK_FORKS_COUNT);
        MOCK_GITHUB_LINK.setHost(MOCK_LINK.getHost());
        MOCK_GITHUB_LINK.setUri(MOCK_LINK.getUri());
        MOCK_GITHUB_LINK.setProtocol(MOCK_LINK.getProtocol());
        MOCK_GITHUB_LINK.setLastUpdatedAt(MOCK_LINK.getLastUpdatedAt());
    }

    @Test
    void testThatGetNonExistsGitHubLinkThrowsException() {
        final Link nonExistsLink = Link.builder()
            .id(MOCK_ID)
            .build();
        assertTrue(gitHubLinkRepository.findById(nonExistsLink.getId()).isEmpty());
    }

    @Test
    void testThatAddExistsLinkReturnedRightResult() {
        GitHubLink savedGitHubLink = Objects.requireNonNull(gitHubLinkRepository.save(MOCK_GITHUB_LINK));

        assertEquals(MOCK_GITHUB_LINK.getId(), savedGitHubLink.getId());
        assertEquals(MOCK_GITHUB_LINK.getDefaultBranch(), savedGitHubLink.getDefaultBranch());
        assertEquals(MOCK_GITHUB_LINK.getForksCount(), savedGitHubLink.getForksCount());
    }

    @Test
    void testThatUpdateGitHubLinkWorksRight() {
        GitHubLink savedGitHubLink = Objects.requireNonNull(gitHubLinkRepository.save(MOCK_GITHUB_LINK));

        savedGitHubLink.setForksCount(3L);
        gitHubLinkRepository.save(savedGitHubLink);

        assertEquals(MOCK_GITHUB_LINK.getId(), savedGitHubLink.getId());
        assertEquals(MOCK_GITHUB_LINK.getDefaultBranch(), savedGitHubLink.getDefaultBranch());
        assertNotEquals(MOCK_FORKS_COUNT, savedGitHubLink.getForksCount());
    }
}
