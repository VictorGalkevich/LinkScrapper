package edu.java.scrapper.client;

import edu.java.backoff.filter.RetryFilter;
import edu.java.dto.request.LinkUpdate;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.scrapper.exception.ApiResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@Slf4j
public class BotClient extends Client {
    public BotClient(String baseUrl, RetryFilter filter) {
        super(baseUrl, filter);
    }

    public Mono<ResponseEntity<Void>> sendUpdate(LinkUpdate upd) {
        log.warn("UPDATES");
        return client().post()
                .uri("/updates")
                .bodyValue(upd)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> {
                            log.error("pizdec");
                            return resp.bodyToMono(ApiErrorResponse.class).map(ApiResponseException::new);
                        }
                )
                .toBodilessEntity();
    }
}
