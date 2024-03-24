package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.jdbc.JdbcGitHubRepository;
import edu.java.scrapper.service.GitHubService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JdbcGitHubService implements GitHubService {
    private final JdbcGitHubRepository gitHubRepository;

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
