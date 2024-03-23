package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.repository.EntityRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import static edu.java.scrapper.domain.jooq.Tables.ASSIGNMENT;
import static edu.java.scrapper.domain.jooq.Tables.CHATS;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository implements EntityRepository<Chat, Long> {
    private final DSLContext dslContext;

    @Override
    public Chat save(Chat obj) {
        return dslContext.insertInto(CHATS, CHATS.ID, CHATS.CREATED_AT)
            .values(
                obj.getId(),
                obj.getCreatedAt()
            )
            .returning(CHATS.fields())
            .fetchOneInto(Chat.class);
    }

    @Override
    public Optional<Chat> findById(Long identifier) {
        try {
            return Optional.ofNullable(dslContext.select(CHATS.fields())
                    .from(CHATS)
                    .where(CHATS.ID.eq(identifier))
                    .fetchOneInto(Chat.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Chat delete(Chat object) {
        return dslContext.deleteFrom(CHATS)
            .where(CHATS.ID.eq(object.getId()))
            .returning(CHATS.fields())
            .fetchOneInto(Chat.class);
    }

    public List<Long> findAllChatsByLinkId(Long id) {
        return dslContext.select(ASSIGNMENT.CHAT_ID)
            .from(ASSIGNMENT)
            .where(ASSIGNMENT.LINK_ID.eq(id))
            .fetchInto(Long.class);
    }
}
