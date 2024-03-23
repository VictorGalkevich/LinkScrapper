package edu.java.scrapper.service;

import edu.java.scrapper.entity.GitHubLink;
import edu.java.scrapper.entity.Link;
import java.util.Optional;

public interface GitHubService {
    Optional<GitHubLink> findLink(Link link);

    GitHubLink save(GitHubLink link);

    void updateLink(GitHubLink link);
}
