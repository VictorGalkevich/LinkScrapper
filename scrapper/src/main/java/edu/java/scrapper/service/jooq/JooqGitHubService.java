package edu.java.scrapper.service.jooq;

import edu.java.scrapper.annotation.TransactionalService;
import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.jooq.JooqGitHubRepository;
import edu.java.scrapper.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@TransactionalService
@RequiredArgsConstructor
public class JooqGitHubService implements GitHubService {
    private final JooqGitHubRepository gitHubRepository;

    @Override
    public Optional<GitHubLink> findLink(Link link) {
        return gitHubRepository.findLink(link);
    }

    @Override
    @Transactional
    public GitHubLink save(GitHubLink link) {
        return gitHubRepository.save(link);
    }

    @Override
    @Transactional
    public void updateLink(GitHubLink link) {
        gitHubRepository.update(link);
    }
}
