package edu.java.scrapper.controller;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.scrapper.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;
    private static final String CHAT_ID_HEADER = "Tg-Chat-Id";

    @GetMapping
    public ResponseEntity<ListLinksResponse> findAllById(@RequestHeader(CHAT_ID_HEADER) Long id) {
        return ok().body(linkService.findAllById(id));
    }

    @PostMapping
    public ResponseEntity<LinkResponse> update(
            @RequestHeader(CHAT_ID_HEADER) Long id,
            @RequestBody AddLinkRequest req
    ) {
        return ok().body(linkService.add(id, req));
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> delete(
            @RequestHeader(CHAT_ID_HEADER) Long id,
            @RequestBody RemoveLinkRequest req
    ) {
        return ok().body(linkService.delete(id, req));
    }

}
