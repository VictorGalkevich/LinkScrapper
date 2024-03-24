package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperIT;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.entity.StackOverflowLink;
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
public class JdbcStackOverflowRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcStackOverflowRepository stackOverflowLinkRepository;
    @Autowired
    private JdbcLinkRepository linkRepository;
    private static final Long MOCK_ID = 123L;
    private static final Link MOCK_LINK = Link.fromUrl(URI.create("https://yandex.ru"));

    @Test
    void testThatGetNonExistsStackOverflowLinkThrowsException() {
        final Link nonExistsLink = Link.builder()
            .id(MOCK_ID)
            .build();
        assertTrue(stackOverflowLinkRepository.findLink(nonExistsLink).isEmpty());
    }

    @Test
    void testThatAddStackOverflowLinkReturnedRightResult() {
        final Link existsLink = linkRepository.save(MOCK_LINK);

        StackOverflowLink stackOverflowLink = new StackOverflowLink()
            .setAnswerCount(1L)
            .setScore(12L);
        stackOverflowLink.setId(existsLink.getId());

        StackOverflowLink savedStackOverflowLink =
            Objects.requireNonNull(stackOverflowLinkRepository.save(stackOverflowLink));

        assertEquals(stackOverflowLink.getId(), savedStackOverflowLink.getId());
        assertEquals(stackOverflowLink.getAnswerCount(), savedStackOverflowLink.getAnswerCount());
        assertEquals(stackOverflowLink.getScore(), savedStackOverflowLink.getScore());
    }

    @Test
    void testThatAddStackOverflowLinkWhenReferenceLinkIsNotExistsThrowsException() {
        StackOverflowLink stackOverflowLink = new StackOverflowLink()
            .setAnswerCount(0L)
            .setScore(1L);
        stackOverflowLink.setId(Long.MAX_VALUE);

        assertThrows(
            DataIntegrityViolationException.class,
            () -> stackOverflowLinkRepository.save(stackOverflowLink)
        );
    }

    @Test
    void testThatUpdateStackOverflowLinkWorksRight() {
        final Link existsLink = linkRepository.save(MOCK_LINK);

        StackOverflowLink stackOverflowLink = new StackOverflowLink()
            .setAnswerCount(1L)
            .setScore(12L);
        stackOverflowLink.setId(existsLink.getId());

        StackOverflowLink savedStackOverflowLink =
            Objects.requireNonNull(stackOverflowLinkRepository.save(stackOverflowLink));
        savedStackOverflowLink.setScore(1L);

        stackOverflowLinkRepository.update(savedStackOverflowLink);

        assertEquals(stackOverflowLink.getId(), savedStackOverflowLink.getId());
        assertEquals(stackOverflowLink.getAnswerCount(), savedStackOverflowLink.getAnswerCount());
        assertNotEquals(stackOverflowLink.getScore(), savedStackOverflowLink.getScore());
    }
}
