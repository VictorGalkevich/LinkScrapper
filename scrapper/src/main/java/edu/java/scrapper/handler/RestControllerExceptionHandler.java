package edu.java.scrapper.handler;

import edu.java.dto.response.ApiErrorResponse;
import edu.java.scrapper.configuration.ExceptionConfig;
import edu.java.scrapper.exception.ChatAlreadyRegisteredException;
import edu.java.scrapper.exception.ChatIsNotRegisteredException;
import edu.java.scrapper.exception.LinkIsAlreadyTrackedException;
import edu.java.scrapper.exception.LinkIsNotTrackedException;
import edu.java.scrapper.exception.ScrapperException;
import edu.java.scrapper.mapper.ApiErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "edu.java.scrapper.controller")
@RequiredArgsConstructor
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final ApiErrorMapper mapper;
    private final ExceptionConfig config;

    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handle(ChatAlreadyRegisteredException e) {
        return build(HttpStatus.CONFLICT, e, config.getAlreadyRegistered());
    }

    @ExceptionHandler(ChatIsNotRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handle(ChatIsNotRegisteredException e) {
        return build(HttpStatus.NOT_FOUND, e, config.getIsNotRegistered());
    }

    @ExceptionHandler(LinkIsAlreadyTrackedException.class)
    public ResponseEntity<ApiErrorResponse> handle(LinkIsAlreadyTrackedException e) {
        return build(HttpStatus.CONFLICT, e, config.getAlreadyTracked());
    }

    @ExceptionHandler(LinkIsNotTrackedException.class)
    public ResponseEntity<ApiErrorResponse> handle(LinkIsNotTrackedException e) {
        return build(HttpStatus.NOT_FOUND, e, config.getIsNotTracked());
    }

    private ResponseEntity<ApiErrorResponse> build(HttpStatus status, ScrapperException e, String description) {
        return ResponseEntity.status(status)
            .body(mapper.map(e, description));
    }
}
