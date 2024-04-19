package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.EntityRepository;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.domain.jooq.Tables.ASSIGNMENT;
import static edu.java.scrapper.domain.jooq.Tables.LINKS;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements EntityRepository<Link, Long> {
    private final DSLContext dslContext;

    @Override
    public Link save(Link obj) {
        return dslContext.insertInto(LINKS, LINKS.URI, LINKS.HOST, LINKS.PROTOCOL, LINKS.LAST_UPDATED_AT)
            .values(
                obj.getUri(),
                obj.getHost(),
                obj.getProtocol(),
                obj.getLastUpdatedAt())
            .returning(LINKS.fields())
            .fetchOneInto(Link.class);
    }

    @Override
    public Optional<Link> findById(Long identifier) {
        try {
            return Optional.ofNullable(dslContext.select(LINKS.fields())
                .from(LINKS)
                .where(LINKS.ID.eq(identifier))
                .fetchOneInto(Link.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Link delete(Link object) {
        return dslContext.deleteFrom(LINKS)
            .where(LINKS.ID.eq(object.getId()))
            .returning(LINKS.fields())
            .fetchOneInto(Link.class);
    }

    public Collection<Link> findByChatId(Long id) {
        return dslContext.select(LINKS.fields())
            .from(LINKS)
            .fetchInto(Link.class);
    }

    public Optional<Link> findByUrl(URI url) {
        try {
            return Optional.ofNullable(dslContext.select(LINKS.fields())
                .from(LINKS)
                .where(LINKS.URI.eq(url.toString()))
                .fetchOneInto(Link.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void connectLink(Long chat, Long link) {
        dslContext.insertInto(ASSIGNMENT, ASSIGNMENT.CHAT_ID, ASSIGNMENT.LINK_ID)
            .values(chat, link)
            .execute();
    }

    public void removeLink(Long chat, Long link) {
        dslContext.deleteFrom(ASSIGNMENT)
            .where(ASSIGNMENT.CHAT_ID.eq(chat).and(ASSIGNMENT.LINK_ID.eq(link)))
            .execute();
    }

    public void update(Link link) {
        dslContext.update(LINKS)
            .set(LINKS.LAST_UPDATED_AT, link.getLastUpdatedAt())
            .where(LINKS.ID.eq(link.getId()))
            .execute();
    }

    public Collection<Link> findAllWithInterval(Duration delay) {
        return dslContext.select(LINKS.fields())
            .from(LINKS)
            .where(LINKS.LAST_UPDATED_AT.lt(OffsetDateTime.now().minus(delay)))
            .fetchInto(Link.class);
    }
}
