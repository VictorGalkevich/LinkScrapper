package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.entity.GitHubLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGitHubRepository extends JpaRepository<GitHubLink, Long> {
}
