package edu.java.scrapper.service.jooq;

import edu.java.scrapper.annotation.TransactionalService;
import edu.java.scrapper.entity.Link;
import edu.java.scrapper.entity.StackOverflowLink;
import edu.java.scrapper.repository.jooq.JooqStackOverflowRepository;
import edu.java.scrapper.service.StackOverflowService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@TransactionalService
@RequiredArgsConstructor
public class JooqStackOverflowService implements StackOverflowService {
    private final JooqStackOverflowRepository stackOverflowRepository;

    @Override
    public Optional<StackOverflowLink> findLink(Link link) {
        return stackOverflowRepository.findLink(link);
    }

    @Override
    @Transactional
    public StackOverflowLink save(StackOverflowLink link) {
        return stackOverflowRepository.save(link);
    }

    @Override
    @Transactional
    public void updateLink(StackOverflowLink link) {
        stackOverflowRepository.update(link);
    }
}
