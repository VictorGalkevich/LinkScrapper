package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.ScrapperIT;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.entity.StackOverflowLink;
import java.net.URI;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ScrapperIT
public class JpaStackOverflowRepositoryTest extends JpaRepositoryTest {
    @Autowired
    private JpaStackOverflowRepository stackOverflowLinkRepository;
    private static final Long MOCK_ID = 123L;
    private static final Long MOCK_SCORE = 1L;
    private static final Long MOCK_ANSWER_COUNT = 1L;
    private static final Link MOCK_LINK = Link.fromUrl(URI.create("https://yandex.ru"));

    private static final StackOverflowLink MOCK_SOF_LINK;

    static {
        MOCK_SOF_LINK = new StackOverflowLink()
            .setScore(MOCK_SCORE)
            .setAnswerCount(MOCK_ANSWER_COUNT);
        MOCK_SOF_LINK.setHost(MOCK_LINK.getHost());
        MOCK_SOF_LINK.setUri(MOCK_LINK.getUri());
        MOCK_SOF_LINK.setProtocol(MOCK_LINK.getProtocol());
        MOCK_SOF_LINK.setLastUpdatedAt(MOCK_LINK.getLastUpdatedAt());
    }

    @Test
    void testThatGetNonExistsStackOverflowLinkThrowsException() {
        assertTrue(stackOverflowLinkRepository.findById(MOCK_ID).isEmpty());
    }

    @Test
    void testThatAddStackOverflowLinkReturnedRightResult() {
        StackOverflowLink savedStackOverflowLink =
            Objects.requireNonNull(stackOverflowLinkRepository.save(MOCK_SOF_LINK));

        assertEquals(MOCK_SOF_LINK.getId(), savedStackOverflowLink.getId());
        assertEquals(MOCK_SOF_LINK.getAnswerCount(), savedStackOverflowLink.getAnswerCount());
        assertEquals(MOCK_SOF_LINK.getScore(), savedStackOverflowLink.getScore());
    }

    @Test
    void testThatUpdateStackOverflowLinkWorksRight() {
        StackOverflowLink savedStackOverflowLink =
            Objects.requireNonNull(stackOverflowLinkRepository.save(MOCK_SOF_LINK));
        savedStackOverflowLink.setScore(2L);

        stackOverflowLinkRepository.save(savedStackOverflowLink);

        assertEquals(MOCK_SOF_LINK.getId(), savedStackOverflowLink.getId());
        assertEquals(MOCK_SOF_LINK.getAnswerCount(), savedStackOverflowLink.getAnswerCount());
        assertNotEquals(MOCK_SCORE, savedStackOverflowLink.getScore());
    }
}
