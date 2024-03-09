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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LinkServiceTest extends ServiceTest {
    private LinkService linkService;
    private static final URI MOCK_URI = URI.create("https://yandex.ru");

    @Override
    void init() {
        super.init();
        linkService = new LinkService(chatRepository);
    }

    @Test
    void testChatDoesNotExist() {
        assertThrows(ChatIsNotRegisteredException.class,
            () -> linkService.findAllById(MOCKED_CHAT_ID));
        assertThrows(ChatIsNotRegisteredException.class,
            () -> linkService.update(MOCKED_CHAT_ID, null));
        assertThrows(ChatIsNotRegisteredException.class,
            () -> linkService.delete(MOCKED_CHAT_ID, null));
    }

    @Test
    void testRemoveLinkWasNotTracked() {
        chatRepository.save(Chat.builder().id(MOCKED_CHAT_ID).links(new ArrayList<>()).build());
        assertThrows(
            LinkIsNotTrackedException.class,
            () -> linkService.delete(MOCKED_CHAT_ID, new RemoveLinkRequest(MOCK_URI)));
    }

    @Test
    void testRemoveLinkWasTracked() {
        Link link = Link.builder()
            .uri(MOCK_URI.toString())
            .build();
        List<Link> links = new ArrayList<>();
        links.add(link);
        chatRepository.save(Chat.builder().id(MOCKED_CHAT_ID).links(links).build());
        LinkResponse delete = linkService.delete(MOCKED_CHAT_ID, new RemoveLinkRequest(MOCK_URI));
        assertNotNull(delete);
        assertTrue(Objects.equals(delete.id(), MOCKED_CHAT_ID) && Objects.equals(MOCK_URI, delete.url()));
        assertThat(chatRepository.findById(MOCKED_CHAT_ID).get().getLinks()).hasSize(0);
    }

    @Test
    void testAddLinkWasAlreadyTracked() {
        Link link = Link.builder()
            .uri(MOCK_URI.toString())
            .build();
        List<Link> links = new ArrayList<>();
        links.add(link);
        chatRepository.save(Chat.builder().id(MOCKED_CHAT_ID).links(links).build());
        assertThrows(
            LinkIsAlreadyTrackedException.class,
            () -> linkService.update(MOCKED_CHAT_ID, new AddLinkRequest(MOCK_URI)));
    }

    @Test
    void testAddLinkWasNotTracked() {
        chatRepository.save(Chat.builder().id(MOCKED_CHAT_ID).links(new ArrayList<>()).build());
        LinkResponse update = linkService.update(MOCKED_CHAT_ID, new AddLinkRequest(MOCK_URI));
        assertNotNull(update);
        assertThat(chatRepository.findById(MOCKED_CHAT_ID).get().getLinks()).hasSize(1);
    }

    @Test
    void testFindAll() {
        Link link = Link.builder()
            .uri(MOCK_URI.toString())
            .build();
        List<Link> links = new ArrayList<>();
        links.add(link);
        chatRepository.save(Chat.builder().id(MOCKED_CHAT_ID).links(links).build());
        ListLinksResponse resp = linkService.findAllById(MOCKED_CHAT_ID);
        assertThat(resp.links()).hasSize(1);
        assertEquals(resp.links().getFirst().url(), MOCK_URI);
    }
}
