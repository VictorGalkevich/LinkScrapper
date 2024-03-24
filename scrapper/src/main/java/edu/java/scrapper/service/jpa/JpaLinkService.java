package edu.java.scrapper.service.jpa;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.exception.ChatIsNotRegisteredException;
import edu.java.scrapper.exception.LinkIsAlreadyTrackedException;
import edu.java.scrapper.exception.LinkIsNotTrackedException;
import edu.java.scrapper.repository.jpa.JpaChatRepository;
import edu.java.scrapper.repository.jpa.JpaLinkRepository;
import edu.java.scrapper.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;

    @Override
    public ListLinksResponse findAllById(Long id) {
        Collection<Link> links = linkRepository.findAllForChat(id);
        return new ListLinksResponse(
            links.stream()
                .map(link -> new LinkResponse(link.getId(), URI.create(link.getUri())))
                .toList(),
            links.size()
        );
    }

    @Override
    @Transactional
    public LinkResponse add(Long id, AddLinkRequest req) {
        Optional<Link> linkWrapper = linkRepository.findByUri(req.link().toString());
        Link link = linkWrapper.orElseGet(() -> linkRepository.save(Link.fromUrl(req.link())));
        Chat currentChat = chatRepository.findById(id)
            .orElseThrow(() -> new ChatIsNotRegisteredException(id));

        if (link.getChats().contains(currentChat)) {
            throw new LinkIsAlreadyTrackedException(req.link(), id);
        }

        link.addChat(currentChat);
        Link savedEntity = linkRepository.save(link);

        return new LinkResponse(savedEntity.getId(), URI.create(savedEntity.getUri()));
    }

    @Override
    @Transactional
    public LinkResponse delete(Long id, RemoveLinkRequest req) {
        Link link = linkRepository.findByUri(req.link().toString())
            .orElseThrow(() -> new LinkIsNotTrackedException(req.link(), id));
        Chat currentChat = chatRepository.findById(id)
            .orElseThrow(() -> new ChatIsNotRegisteredException(id));

        if (!link.getChats().contains(currentChat)) {
            throw new LinkIsNotTrackedException(req.link(), id);
        }

        link.removeChat(currentChat);
        Link savedEntity = linkRepository.save(link);

        return new LinkResponse(savedEntity.getId(), URI.create(savedEntity.getUri()));
    }

    @Override
    @Transactional
    public void updateLink(Link link) {
        linkRepository.saveAndFlush(link);
    }

    @Override
    public Collection<Link> findAllWithDelay(Duration delay) {
        return linkRepository.findAllByLastUpdatedAtBefore(OffsetDateTime.now()
            .minus(delay));
    }
}
