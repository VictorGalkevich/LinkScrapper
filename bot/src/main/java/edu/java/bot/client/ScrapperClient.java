package edu.java.bot.client;

import edu.java.backoff.filter.RetryFilter;
import edu.java.bot.configuration.ScrapperConfig;
import edu.java.bot.exception.ApiResponseException;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.ApiErrorResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class ScrapperClient extends Client {
    private final ScrapperConfig config;

    public ScrapperClient(String baseUrl, ScrapperConfig config, RetryFilter filter) {
        super(baseUrl, filter);
        this.config = config;
    }

    public Mono<ResponseEntity<Void>> register(Long tgChatId) {
        return client().post()
                .uri(config.getChat(), tgChatId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(ApiErrorResponse.class).map(ApiResponseException::new)
                )
                .toBodilessEntity();
    }

    public Mono<ResponseEntity<Void>> delete(Long tgChatId) {
        return client().delete()
                .uri(config.getChat(), tgChatId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(ApiErrorResponse.class).map(ApiResponseException::new)
                )
                .toBodilessEntity();
    }

    public Mono<ResponseEntity<ListLinksResponse>> getAllLinks(Long tgChatId) {
        return client().get()
                .uri(config.getLinks())
                .header(config.getHeader(), tgChatId.toString())
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(ApiErrorResponse.class).map(ApiResponseException::new)
                )
                .toEntity(ListLinksResponse.class);
    }

    public Mono<ResponseEntity<LinkResponse>> addLink(Long tgChatId, AddLinkRequest req) {
        return client().post()
                .uri(config.getLinks())
                .header(config.getHeader(), tgChatId.toString())
                .bodyValue(req)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(ApiErrorResponse.class).map(ApiResponseException::new)
                )
                .toEntity(LinkResponse.class);
    }

    public Mono<ResponseEntity<ListLinksResponse>> remove(Long tgChatId, RemoveLinkRequest req) {
        return client().method(HttpMethod.DELETE)
                .uri(config.getLinks())
                .header(config.getHeader(), tgChatId.toString())
                .bodyValue(req)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> resp.bodyToMono(ApiErrorResponse.class).map(ApiResponseException::new)
                )
                .toEntity(ListLinksResponse.class);
    }
}
