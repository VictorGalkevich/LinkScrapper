package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.LinkUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import static edu.java.scrapper.repository.jdbc.SqlQueries.ADD_GITHUB_LINK;
import static edu.java.scrapper.repository.jdbc.SqlQueries.FIND_GITHUB_LINK;
import static edu.java.scrapper.repository.jdbc.SqlQueries.UPDATE_LINK;

@Repository
@RequiredArgsConstructor
public class JdbcGitHubRepository implements LinkUpdateRepository<GitHubLink> {
    private final JdbcClient jdbcClient;
    private final BeanPropertyRowMapper<GitHubLink> mapper = new BeanPropertyRowMapper<>(GitHubLink.class);

    @Override
    public Optional<GitHubLink> findLink(Link link) {
        try {
            return Optional.of(jdbcClient.sql(FIND_GITHUB_LINK)
                .params(link.getId())
                .query(mapper)
                .single());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public GitHubLink save(GitHubLink link) {
        return jdbcClient.sql(ADD_GITHUB_LINK)
            .params(
                link.getId(),
                link.getDefaultBranch(),
                link.getForksCount()
            )
            .query(mapper).single();
    }

    @Override
    public void update(GitHubLink link) {
        jdbcClient.sql(UPDATE_LINK)
            .params(
                link.getDefaultBranch(),
                link.getForksCount(),
                link.getId()
            ).update();
    }
}
