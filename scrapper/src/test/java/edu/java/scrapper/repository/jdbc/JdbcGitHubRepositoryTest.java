package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperIT;
import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import java.net.URI;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ScrapperIT
public class JdbcGitHubRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcGitHubRepository gitHubLinkRepository;
    @Autowired
    private JdbcLinkRepository linkRepository;
    private static final Long MOCK_ID = 123L;
    private static final Link MOCK_LINK = Link.fromUrl(URI.create("https://yandex.ru"));
    private static final String MOCK_BRANCH = "master";

    @Test
    void testThatGetNonExistsGitHubLinkThrowsException() {
        final Link nonExistsLink = Link.builder()
            .id(MOCK_ID)
            .build();
        assertTrue(gitHubLinkRepository.findLink(nonExistsLink).isEmpty());
    }

    @Test
    void testThatAddExistsLinkReturnedRightResult() {
        final Link existsLink = linkRepository.save(MOCK_LINK);

        GitHubLink gitHubLink = new GitHubLink()
            .setDefaultBranch("master")
            .setForksCount(1L);
        gitHubLink.setId(existsLink.getId());
        GitHubLink savedGitHubLink = Objects.requireNonNull(gitHubLinkRepository.save(gitHubLink));

        assertEquals(gitHubLink.getId(), savedGitHubLink.getId());
        assertEquals(gitHubLink.getDefaultBranch(), savedGitHubLink.getDefaultBranch());
        assertEquals(gitHubLink.getForksCount(), savedGitHubLink.getForksCount());
    }

    @Test
    void testtThatAddGitHubLinkWhenReferenceLinkIsNotExistsThrowsException() {
        GitHubLink gitHubLink = new GitHubLink()
            .setDefaultBranch(MOCK_BRANCH)
            .setForksCount(1L);
        gitHubLink.setId(Long.MAX_VALUE);

        assertThrows(DataIntegrityViolationException.class, () -> gitHubLinkRepository.save(gitHubLink));
    }

    @Test
    void testThatUpdateGitHubLinkWorksRight() {
        final Link existsLink = linkRepository.save(MOCK_LINK);

        final GitHubLink gitHubLink = new GitHubLink()
            .setDefaultBranch(MOCK_BRANCH)
            .setForksCount(2L);
        gitHubLink.setId(existsLink.getId());

        GitHubLink savedGitHubLink = Objects.requireNonNull(gitHubLinkRepository.save(gitHubLink));

        savedGitHubLink.setForksCount(3L);
        gitHubLinkRepository.update(savedGitHubLink);

        assertEquals(gitHubLink.getId(), savedGitHubLink.getId());
        assertEquals(gitHubLink.getDefaultBranch(), savedGitHubLink.getDefaultBranch());
        assertNotEquals(gitHubLink.getForksCount(), savedGitHubLink.getForksCount());
    }
}
