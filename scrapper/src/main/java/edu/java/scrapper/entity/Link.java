package edu.java.scrapper.entity;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Link {
    private Long id;
    private String uri;
    private String host;
    private String protocol;
    private List<Chat> chats;
    private OffsetDateTime lastUpdatedAt;

    public static Link fromUrl(URI url) {
        return Link.builder()
                .uri(url.toString())
                .host(url.getHost())
                .protocol(url.getScheme())
                .lastUpdatedAt(OffsetDateTime.now())
                .build();
    }
}
