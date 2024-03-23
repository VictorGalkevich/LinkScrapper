--liquibase formatted sql

--changeset VictorGalkevich:1
CREATE TABLE IF NOT EXISTS github_links
(
    id             BIGINT REFERENCES links (id) PRIMARY KEY ,
    default_branch VARCHAR(64),
    forks_count    BIGINT
);

--changeset VictorGalkevich:2
CREATE TABLE IF NOT EXISTS stackoverflow_links
(
    id           BIGINT REFERENCES links (id) PRIMARY KEY,
    answer_count BIGINT,
    score        BIGINT
);
