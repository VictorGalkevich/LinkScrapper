package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.configuration.access.AccessType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class JpaRepositoryTest extends IntegrationTest {
    @DynamicPropertySource
    public static void setJdbcAccessType(DynamicPropertyRegistry registry) {
        setAccessType(registry, AccessType.JPA);
    }
}
