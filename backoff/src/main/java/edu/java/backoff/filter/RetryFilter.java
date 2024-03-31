package edu.java.backoff.filter;

import edu.java.backoff.type.Backoff;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.Set;

@RequiredArgsConstructor
public class RetryFilter implements ExchangeFilterFunction {
    private final Backoff backoff;
    private final Set<HttpStatus> httpStatuses;
    private final int attemptsThreshold;
    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return retry(request, next, 1);
    }

    private Mono<ClientResponse> retry(ClientRequest request, ExchangeFunction next, int atmpt) {
        return next.exchange(request)
            .flatMap(resp -> {
                HttpStatus status = (HttpStatus) resp.statusCode();
                if (httpStatuses.contains(status) && atmpt <= attemptsThreshold) {
                    Duration timeToWait = backoff.calculateWaitTime(atmpt);
                    Mono<ClientResponse> response = Mono.defer(
                        () -> retry(request, next, atmpt + 1)
                    );
                    return Mono.delay(timeToWait).then(response);
                }
                return Mono.just(resp);
            });
    }
}
