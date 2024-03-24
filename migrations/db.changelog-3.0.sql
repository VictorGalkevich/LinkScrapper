--liquibase formatted sql

--changeset VictorGalkevich:1
ALTER TABLE links
ALTER COLUMN host TYPE VARCHAR(256);
