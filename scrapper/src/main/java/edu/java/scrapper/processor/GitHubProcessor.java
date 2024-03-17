package edu.java.scrapper.processor;

import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.dto.GitHubResponseDto;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GitHubProcessor extends Processor {
    private final GitHubClient client;

    public GitHubProcessor(GitHubClient client, LinkService service) {
        super("github.com", service);
        this.client = client;
    }

    @Override
    public Mono<String> process(Link link) {
        URI uri = URI.create(link.getUri());
        return client.getRepositoryInfo(uri.getPath())
                .mapNotNull(resp -> {
                    if (hasUpdates(resp, link)) {
                        link.setLastUpdatedAt(resp.updatedAt());
                        service().updateLink(link);
                        return "Repository has recent changes";
                    }
                    return null;
                });

    }

    private boolean hasUpdates(GitHubResponseDto resp, Link link) {
        return resp.updatedAt().isAfter(OffsetDateTime.from(link.getLastUpdatedAt()));
    }
}
