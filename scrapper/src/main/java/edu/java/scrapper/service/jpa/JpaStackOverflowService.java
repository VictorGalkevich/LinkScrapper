package edu.java.scrapper.service.jpa;

import edu.java.scrapper.entity.Link;
import edu.java.scrapper.entity.StackOverflowLink;
import edu.java.scrapper.repository.jpa.JpaStackOverflowRepository;
import edu.java.scrapper.service.StackOverflowService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaStackOverflowService implements StackOverflowService {
    private final JpaStackOverflowRepository repository;

    @Override
    public Optional<StackOverflowLink> findLink(Link link) {
        return repository.findById(link.getId());
    }

    @Override
    public StackOverflowLink save(StackOverflowLink link) {
        return repository.save(link);
    }

    @Override
    public void updateLink(StackOverflowLink link) {
        repository.saveAndFlush(link);
    }
}
