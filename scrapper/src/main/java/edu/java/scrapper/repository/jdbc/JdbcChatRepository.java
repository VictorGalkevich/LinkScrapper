package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.repository.EntityRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.repository.jdbc.SqlQueries.ADD_CHAT;
import static edu.java.scrapper.repository.jdbc.SqlQueries.DELETE_CHAT;
import static edu.java.scrapper.repository.jdbc.SqlQueries.FIND_CHAT_BY_ID;
import static edu.java.scrapper.repository.jdbc.SqlQueries.FIND_RELATED_CHATS;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository implements EntityRepository<Chat, Long> {
    private final JdbcClient jdbcClient;
    private final BeanPropertyRowMapper<Chat> mapper = new BeanPropertyRowMapper<>(Chat.class);

    @Override
    public Chat save(Chat obj) {
        return jdbcClient.sql(ADD_CHAT)
                .params(
                        obj.getId(),
                        obj.getCreatedAt()
                ).query(mapper).single();
    }

    @Override
    public Optional<Chat> findById(Long identifier) {
        try {
            return Optional.of(jdbcClient.sql(FIND_CHAT_BY_ID)
                .params(identifier)
                .query(mapper)
                .single());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Chat delete(Chat object) {
        return jdbcClient.sql(DELETE_CHAT)
                .params(object.getId(),
                        object.getCreatedAt())
                .query(mapper).single();
    }

    public List<Long> findAllChatsByLinkId(Long id) {
        return jdbcClient.sql(FIND_RELATED_CHATS)
                .params(id)
                .query((rs, rn) -> rs.getLong("chat_id")).list();
    }
}
