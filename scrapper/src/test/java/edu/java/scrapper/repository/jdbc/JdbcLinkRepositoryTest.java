package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.ScrapperIT;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ScrapperIT
public class JdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcLinkRepository linkRepository;
    private static final Link TEST_LINK;

    static {
        TEST_LINK = Link.builder()
            .uri("https://github.com")
            .protocol("https")
            .host("github.com")
            .lastUpdatedAt(OffsetDateTime.now())
            .build();
    }

    @DynamicPropertySource
    public static void setJdbcAccessType(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jdbc");
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
