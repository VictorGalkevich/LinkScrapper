package edu.java.scrapper.exception;

import org.springframework.http.HttpStatus;

public class ChatIsNotRegisteredException extends ScrapperException {
    public ChatIsNotRegisteredException(Long id) {
        super(
                "Chat with id %d doesn't exist".formatted(id),
                HttpStatus.NOT_FOUND
        );
    }
}
