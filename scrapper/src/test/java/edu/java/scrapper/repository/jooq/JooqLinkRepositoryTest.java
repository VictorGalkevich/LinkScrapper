package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperIT;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ScrapperIT
public class JooqLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqLinkRepository linkRepository;
    private static final Link TEST_LINK;

    static {
        TEST_LINK = Link.builder()
            .uri("https://github.com")
            .protocol("https")
            .host("github.com")
            .lastUpdatedAt(OffsetDateTime.now())
            .build();
    }

    @Test
    void testAddPositive() {
        Link save = linkRepository.save(TEST_LINK);
        assertEquals(save.getUri(), TEST_LINK.getUri());
    }

    @Test
    void testDeletePositive() {
        Link save = linkRepository.save(TEST_LINK);
        Link delete = linkRepository.delete(save);
        assertEquals(TEST_LINK.getUri(), delete.getUri());
    }

    @Test
    void testFindByIdExists() {
        Link save = linkRepository.save(TEST_LINK);
        assertTrue(linkRepository.findById(save.getId()).isPresent());
    }

    @Test
    void testFindByIdDoesNotExist() {
        assertTrue(linkRepository.findById(1L).isEmpty());
    }
}
