package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.LinkUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import static edu.java.scrapper.domain.jooq.tables.GithubLinks.*;

@Repository
@RequiredArgsConstructor
public class JooqGitHubRepository implements LinkUpdateRepository<GitHubLink> {
    private final DSLContext dslContext;

    @Override
    public Optional<GitHubLink> findLink(Link link) {
        return Optional.ofNullable(dslContext.select(GITHUB_LINKS.fields())
            .from(GITHUB_LINKS)
            .where(GITHUB_LINKS.ID.eq(link.getId()))
            .fetchOneInto(GitHubLink.class));
    }

    @Override
    public GitHubLink save(GitHubLink link) {
        return dslContext.insertInto(GITHUB_LINKS, GITHUB_LINKS.ID, GITHUB_LINKS.DEFAULT_BRANCH, GITHUB_LINKS.FORKS_COUNT)
            .values(link.getId(), link.getDefaultBranch(), link.getForksCount())
            .returning(GITHUB_LINKS.fields())
            .fetchOneInto(GitHubLink.class);
    }

    @Override
    public void update(GitHubLink link) {
        dslContext.update(GITHUB_LINKS)
            .set(GITHUB_LINKS.DEFAULT_BRANCH, link.getDefaultBranch())
            .set(GITHUB_LINKS.FORKS_COUNT, link.getForksCount())
            .where(GITHUB_LINKS.ID.eq(link.getId()))
            .execute();
    }
}
