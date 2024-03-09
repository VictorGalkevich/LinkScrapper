package edu.java.scrapper.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ScrapperException extends RuntimeException {
    private final HttpStatus status;

    public ScrapperException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
