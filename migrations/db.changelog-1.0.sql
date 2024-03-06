--liquibase formatted sql

--changeset VictorGalkevich:1
CREATE TABLE IF NOT EXISTS chats
(
    id            BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(64)
);

--changeset VictorGalkevich:2
CREATE TABLE IF NOT EXISTS links
(
    id              BIGSERIAL PRIMARY KEY,
    url             VARCHAR(256) UNIQUE NOT NULL,
    host             VARCHAR(16) NOT NULL,
    protocol             VARCHAR(16) NOT NULL,
    last_updated_at TIMESTAMP WITH TIME ZONE
);

--changeset VictorGalkevich:3
CREATE TABLE IF NOT EXISTS assignment
(
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT REFERENCES chats (id) ON DELETE CASCADE,
    link_id BIGINT REFERENCES links (id) ON DELETE CASCADE,
    UNIQUE (chat_id, link_id)
)
