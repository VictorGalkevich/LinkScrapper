package edu.java.scrapper.client;

import edu.java.backoff.filter.RetryFilter;
import edu.java.scrapper.dto.StackOverflowResponseDto;
import reactor.core.publisher.Mono;

public class StackOverflowClient extends Client {
    public StackOverflowClient(String baseUrl, RetryFilter filter) {
        super(baseUrl, filter);
    }

    public Mono<StackOverflowResponseDto> getQuestionInfo(Long id) {
        return client().get()
                .uri("/questions/" + id + "?site=stackoverflow")
                .retrieve()
                .bodyToMono(StackOverflowResponseDto.class);
    }
}
