package edu.java.scrapper.processor;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.dto.GitHubResponseDto;
import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.service.GitHubService;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GitHubProcessor extends Processor {
    private final GitHubClient client;
    private final GitHubService gitHubService;

    public GitHubProcessor(
        GitHubClient client,
        LinkService jooqLinkService,
        GitHubService jooqGitHubService
    ) {
        super("github.com", jooqLinkService);
        this.client = client;
        this.gitHubService = jooqGitHubService;
    }

    @Override
    public Mono<String> process(Link link) {
        URI uri = URI.create(link.getUri());
        Optional<GitHubLink> previous = gitHubService.findLink(link);
        return client.getRepositoryInfo(uri.getPath())
            .mapNotNull(resp -> {
                StringBuilder message = new StringBuilder();
                if (hasUpdates(resp, link)) {
                    link.setLastUpdatedAt(resp.updatedAt());
                    service().updateLink(link);
                    message.append("Repository has recent changes");
                }
                boolean isDirty = false;
                GitHubLink gitHubLink = getEntity(previous, link);

                if (previous.isEmpty() || !gitHubLink.getDefaultBranch().equals(resp.defaultBranch())) {
                    isDirty = true;
                    if (previous.isPresent()) {
                        message.append("The main branch has been changed: %s -> %s\n".formatted(
                            gitHubLink.getDefaultBranch(),
                            resp.defaultBranch()
                        ));
                    }
                    gitHubLink.setDefaultBranch(resp.defaultBranch());
                }

                if (previous.isEmpty() || !gitHubLink.getForksCount().equals(resp.forksCount())) {
                    isDirty = true;
                    if (previous.isPresent()) {
                        message.append("The fork number has changed: %d -> %d\n"
                            .formatted(gitHubLink.getForksCount(), resp.forksCount()));
                    }
                    gitHubLink.setForksCount(resp.forksCount());
                }

                if (isDirty) {
                    saveOrUpdate(gitHubLink, previous.isEmpty());
                }

                return message.isEmpty() ? null : message.toString();
            });
    }

    private boolean hasUpdates(GitHubResponseDto resp, Link link) {
        return resp.updatedAt().isAfter(OffsetDateTime.from(link.getLastUpdatedAt()));
    }

    private void saveOrUpdate(GitHubLink gitHubLink, boolean isNewEntity) {
        if (isNewEntity) {
            gitHubService.save(gitHubLink);
        } else {
            gitHubService.updateLink(gitHubLink);
        }
    }

    private GitHubLink getEntity(Optional<GitHubLink> repositoryLink, Link link) {
        if (repositoryLink.isPresent()) {
            return repositoryLink.get();
        }

        GitHubLink gitHubLink = new GitHubLink();
        gitHubLink.setId(link.getId());

        return gitHubLink;
    }
}
