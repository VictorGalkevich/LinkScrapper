package edu.java.scrapper.service.jpa;

import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.jpa.JpaGitHubRepository;
import edu.java.scrapper.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaGitHubService implements GitHubService {
    private final JpaGitHubRepository repository;

    @Override
    public Optional<GitHubLink> findLink(Link link) {
        return repository.findById(link.getId());
    }

    @Override
    @Transactional
    public GitHubLink save(GitHubLink link) {
        return repository.save(link);
    }

    @Override
    @Transactional
    public void updateLink(GitHubLink link) {
        repository.saveAndFlush(link);
    }
}
