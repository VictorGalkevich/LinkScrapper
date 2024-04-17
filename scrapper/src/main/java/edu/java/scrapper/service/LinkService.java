package edu.java.scrapper.service;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.scrapper.entity.Link;
import java.time.Duration;
import java.util.Collection;

public interface LinkService {

    ListLinksResponse findAllById(Long id);

    LinkResponse add(Long id, AddLinkRequest req);

    LinkResponse delete(Long id, RemoveLinkRequest req);

    void updateLink(Link link);

    Collection<Link> findAllWithDelay(Duration delay);
}
