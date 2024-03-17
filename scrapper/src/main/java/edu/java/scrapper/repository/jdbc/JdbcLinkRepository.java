package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.entity.Link;
import edu.java.scrapper.repository.ScrapperRepository;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.repository.jdbc.SqlQueries.*;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements ScrapperRepository<Link, Long> {
    private final JdbcClient jdbcClient;
    private final BeanPropertyRowMapper<Link> mapper = new BeanPropertyRowMapper<>(Link.class);

    @Override
    public Link save(Link obj) {
        return jdbcClient.sql(ADD_LINK)
                .params(obj.getUri(),
                        obj.getHost(),
                        obj.getProtocol(),
                        obj.getLastUpdatedAt())
                .query(mapper).single();
    }

    @Override
    public Optional<Link> findById(Long identifier) {
        return Optional.empty();
    }

    @Override
    public Link delete(Link object) {
        return jdbcClient.sql(DELETE_LINK)
                .params(object.getId())
                .query(mapper).single();
    }

    public Collection<Link> findByCompanyId(Long id) {
        return jdbcClient.sql(FIND_CONNECTED_LINKS)
                .params(id)
                .query(mapper).list();
    }

    public Optional<Link> findByUrl(URI url) {
        try {
            return Optional.of(jdbcClient.sql(FIND_LINK)
                    .params(url.toString())
                    .query(mapper)
                    .single());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void connectLink(Long chat, Long link) {
        jdbcClient.sql(CONNECT_LINK_TO_CHAT)
                .params(chat,
                        link)
                .update();
    }

    public void removeLink(Long chat, Long link) {
        jdbcClient.sql(REMOVE_LINK_FROM_CHAT)
                .params(chat,
                        link)
                .update();
    }

    public void update(Link link) {
        jdbcClient.sql(CONNECT_LINK_TO_CHAT)
                .params(link.getLastUpdatedAt(),
                        link.getId())
                .update();
    }

    public Collection<Link> findAllWithInterval(Duration delay) {
        return jdbcClient.sql(FIND_ALL_BY_DELAY)
                .params(Timestamp.from(Instant.now().minusSeconds(delay.toSeconds())))
                .query(mapper).list();
    }
}
