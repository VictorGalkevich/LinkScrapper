package edu.java.scrapper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "github_links")
public class GitHubLink extends Link {
    @Column(name = "default_branch")
    private String defaultBranch;

    @Column(name = "forks_count")
    private Long forksCount;
}
