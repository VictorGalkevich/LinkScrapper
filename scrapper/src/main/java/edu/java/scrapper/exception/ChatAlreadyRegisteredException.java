package edu.java.scrapper.exception;

import org.springframework.http.HttpStatus;

public class ChatAlreadyRegisteredException extends ScrapperException {
    public ChatAlreadyRegisteredException(Long id) {
        super(
            "Chat with id %s already exists".formatted(id),
            HttpStatus.CONFLICT
        );
    }
}
