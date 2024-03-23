package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.entity.Link;
import edu.java.scrapper.entity.StackOverflowLink;
import edu.java.scrapper.repository.LinkUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import static edu.java.scrapper.domain.jooq.Tables.STACKOVERFLOW_LINKS;

@Repository
@RequiredArgsConstructor
public class JooqStackOverflowRepository implements LinkUpdateRepository<StackOverflowLink> {
    private final DSLContext dslContext;

    @Override
    public Optional<StackOverflowLink> findLink(Link link) {
        return Optional.ofNullable(dslContext.select(STACKOVERFLOW_LINKS.fields())
            .from(STACKOVERFLOW_LINKS)
            .where(STACKOVERFLOW_LINKS.ID.eq(link.getId()))
            .fetchOneInto(StackOverflowLink.class));
    }

    @Override
    public StackOverflowLink save(StackOverflowLink link) {
        return dslContext.insertInto(
                STACKOVERFLOW_LINKS,
                STACKOVERFLOW_LINKS.ID,
                STACKOVERFLOW_LINKS.ANSWER_COUNT,
                STACKOVERFLOW_LINKS.SCORE
            )
            .values(link.getId(), link.getAnswerCount(), link.getScore())
            .returning(STACKOVERFLOW_LINKS.fields())
            .fetchOneInto(StackOverflowLink.class);
    }

    @Override
    public void update(StackOverflowLink link) {
        dslContext.update(STACKOVERFLOW_LINKS)
            .set(STACKOVERFLOW_LINKS.ANSWER_COUNT, link.getAnswerCount())
            .set(STACKOVERFLOW_LINKS.SCORE, link.getScore())
            .execute();
    }
}
