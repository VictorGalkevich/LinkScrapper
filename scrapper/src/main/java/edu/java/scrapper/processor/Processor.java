package edu.java.scrapper.processor;

import edu.java.scrapper.entity.Link;
import edu.java.scrapper.service.LinkService;
import reactor.core.publisher.Mono;

public abstract class Processor {
    private final String host;
    private final LinkService linkService;

    protected Processor(String host, LinkService service) {
        this.host = host;
        this.linkService = service;
    }

    public abstract Mono<String> process(Link link);

    public boolean supports(Link link) {
        return this.host.equals(link.getHost());
    }

    public LinkService service() {
        return linkService;
    }
}
