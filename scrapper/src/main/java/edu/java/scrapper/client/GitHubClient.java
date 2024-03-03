package edu.java.scrapper.client;

import edu.java.scrapper.dto.GitHubResponseDto;
import reactor.core.publisher.Mono;

public class GitHubClient extends Client {
    public GitHubClient(String baseUrl) {
        super(baseUrl);
    }

    public Mono<GitHubResponseDto> getRepositoryInfo(String ownerAndRepo) {
        return client().get()
            .uri("/repos/" + ownerAndRepo)
            .retrieve()
            .bodyToMono(GitHubResponseDto.class);
    }
}
