package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.entity.Link;
import edu.java.scrapper.entity.StackOverflowLink;
import edu.java.scrapper.repository.jdbc.JdbcStackOverflowRepository;
import edu.java.scrapper.service.StackOverflowService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JdbcStackOverflowService implements StackOverflowService {
    private final JdbcStackOverflowRepository stackOverflowRepository;

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
