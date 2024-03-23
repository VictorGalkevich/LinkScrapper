package edu.java.scrapper.service;

import edu.java.scrapper.entity.Link;
import edu.java.scrapper.entity.StackOverflowLink;
import java.util.Optional;

public interface StackOverflowService {
    Optional<StackOverflowLink> findLink(Link link);

    StackOverflowLink save(StackOverflowLink link);

    void updateLink(StackOverflowLink link);
}
