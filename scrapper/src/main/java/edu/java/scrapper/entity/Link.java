package edu.java.scrapper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "links")
@Inheritance(strategy = InheritanceType.JOINED)
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uri")
    private String uri;

    @Column(name = "host")
    private String host;

    @Column(name = "protocol")
    private String protocol;

    @ManyToMany(mappedBy = "links")
    @Builder.Default
    private List<Chat> chats = new ArrayList<>();

    @Column(name = "last_updated_at")
    private OffsetDateTime lastUpdatedAt;

    public static Link fromUrl(URI url) {
        return Link.builder()
                .uri(url.toString())
                .host(url.getHost())
                .protocol(url.getScheme())
                .lastUpdatedAt(OffsetDateTime.now())
                .build();
    }

    public void addChat(Chat chat) {
        chats.add(chat);
        chat.getLinks().add(this);
    }

    public void removeChat(Chat chat) {
        chats.remove(chat);
        chat.getLinks().remove(this);
    }
}
