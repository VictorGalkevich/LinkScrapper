package edu.java.scrapper.exception;

import java.net.URI;
import org.springframework.http.HttpStatus;

public class LinkIsAlreadyTrackedException extends ScrapperException {
    public LinkIsAlreadyTrackedException(URI uri, Long id) {
        super(
                "Link %s is already being tracked by %d".formatted(uri.toString(), id),
                HttpStatus.CONFLICT
        );
    }
}
