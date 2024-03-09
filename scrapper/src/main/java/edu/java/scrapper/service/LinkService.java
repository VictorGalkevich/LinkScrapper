package edu.java.scrapper.service;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.exception.ChatIsNotRegisteredException;
import edu.java.scrapper.exception.LinkIsAlreadyTrackedException;
import edu.java.scrapper.exception.LinkIsNotTrackedException;
import edu.java.scrapper.repository.ChatRepository;
import java.net.URI;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final ChatRepository chatRepository;

    public ListLinksResponse findAllById(Long id) {
        List<LinkResponse> links = findChatById(id)
            .getLinks().stream()
            .map(link -> new LinkResponse(link.getId(), URI.create(link.getUri())))
            .toList();
        return new ListLinksResponse(links, links.size());
    }

    public LinkResponse update(Long id, AddLinkRequest req) {
        Chat chat = findChatById(id);
        List<Link> links = chat.getLinks();
        URI uri = req.link();

        if (links.stream().anyMatch(x -> x.getUri().equals(uri.toString()))) {
            throw new LinkIsAlreadyTrackedException(uri, id);
        }

        Link link = Link.builder()
            .id(new Random().nextLong(Long.MAX_VALUE))
            .uri(uri.toString())
            .protocol(uri.getScheme())
            .host(uri.getHost())
            .build();
        links.add(link);
        return new LinkResponse(link.getId(), uri);
    }

    public LinkResponse delete(Long id, RemoveLinkRequest req) {
        Chat chat = findChatById(id);
        List<Link> links = chat.getLinks();
        URI uri = req.link();

        for (int i = 0; i < links.size(); i++) {
            if (links.get(i).getUri().equals(uri.toString())) {
                links.remove(i);
                return new LinkResponse(id, uri);
            }
        }
        throw new LinkIsNotTrackedException(uri, id);
    }

    private Chat findChatById(Long id) {
        return chatRepository.findById(id)
            .orElseThrow(() -> new ChatIsNotRegisteredException(id));
    }
}
