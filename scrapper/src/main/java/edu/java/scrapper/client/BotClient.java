package edu.java.scrapper.client;

import edu.java.backoff.filter.RetryFilter;
import edu.java.dto.request.LinkUpdate;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.scrapper.exception.ApiResponseException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class BotClient extends Client {
    public BotClient(String baseUrl, RetryFilter filter) {
        super(baseUrl, filter);
    }

    public Mono<ResponseEntity<Void>> sendUpdate(LinkUpdate upd) {
        return client().post()
                .uri("/updates")
                .bodyValue(upd)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(ApiErrorResponse.class).map(ApiResponseException::new)
                )
                .toBodilessEntity();
    }
}
