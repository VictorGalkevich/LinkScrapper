package edu.java.scrapper.exception;

import java.net.URI;
import org.springframework.http.HttpStatus;

public class LinkIsNotTrackedException extends ScrapperException {
    public LinkIsNotTrackedException(URI uri, Long id) {
        super(
            "Link %s is not being tracked by %d".formatted(uri.toString(), id),
            HttpStatus.NOT_FOUND
        );
    }
}
