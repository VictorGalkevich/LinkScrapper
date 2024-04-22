package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.entity.StackOverflowLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStackOverflowRepository extends JpaRepository<StackOverflowLink, Long> {
}
