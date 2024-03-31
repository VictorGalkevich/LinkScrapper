package edu.java.scrapper.client;

import edu.java.backoff.filter.RetryFilter;
import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.dto.GitHubResponseDto;
import reactor.core.publisher.Mono;

public class GitHubClient extends Client {
    private final String token;
    public GitHubClient(String baseUrl, RetryFilter filter, String token) {
        super(baseUrl, filter);
        this.token = token;
    }

    public Mono<GitHubResponseDto> getRepositoryInfo(String ownerAndRepo) {
        return client().get()
                .uri("/repos/" + ownerAndRepo)
            .header("Authorizartion", "Bearer %s".formatted(token))
                .retrieve()
                .bodyToMono(GitHubResponseDto.class);
    }
}
