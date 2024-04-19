package edu.java.scrapper.repository.jdbc;

public abstract class SqlQueries {
    public static final String ADD_CHAT = "INSERT INTO chats (id, created_at) VALUES (?, ?) RETURNING *";
    public static final String ADD_LINK = """
            INSERT INTO links (uri, host, protocol, last_updated_at)
            VALUES (?, ?, ?, ?) RETURNING *
            """;
    public static final String DELETE_CHAT = "DELETE FROM chats WHERE id=? RETURNING *";
    public static final String DELETE_LINK = "DELETE FROM links WHERE id=? RETURNING *";
    public static final String FIND_LINK = "SELECT * FROM links WHERE uri=?";
    public static final String FIND_CONNECTED_LINKS = """
            SELECT * FROM links
            INNER JOIN assignment a ON links.id = a.link_id
            WHERE a.chat_id=?
            """;

    public static final String FIND_RELATED_CHATS = """
            SELECT chat_id FROM assignment
            WHERE link_id=?
            """;

    public static final String CONNECT_LINK_TO_CHAT = "INSERT INTO assignment (chat_id, link_id) VALUES (?, ?)";
    public static final String REMOVE_LINK_FROM_CHAT = """
            DELETE FROM assignment
            WHERE chat_id=?
            AND link_id=?
            """;

    public static final String UPDATE_LINK = """
            UPDATE links
            SET last_updated_at=?
            WHERE id=?
            """;

    public static final String FIND_ALL_BY_DELAY = """
            SELECT * FROM links
            WHERE last_updated_at <?
            """;

    public static final String FIND_LINK_BY_ID = """
            SELECT * FROM links
            WHERE id=?
            """;

    public static final String FIND_CHAT_BY_ID = """
            SELECT * FROM chats
            WHERE id=?
            """;

    public static final String FIND_GITHUB_LINK = """
        SELECT * FROM github_links
        WHERE id=?
        """;

    public static final String FIND_SOF_LINK = """
        SELECT * FROM stackoverflow_links
        WHERE id=?
        """;

    public static final String ADD_GITHUB_LINK = """
        INSERT INTO github_links (id, default_branch, forks_count)
        VALUES (?,?,?) RETURNING *
        """;

    public static final String ADD_SOF_LINK = """
        INSERT INTO stackoverflow_links (id, answer_count, score)
        VALUES (?,?,?) RETURNING *
        """;

    public static final String UPDATE_GITHUB_LINK = """
        UPDATE github_links
        SET default_branch=?, forks_count=? WHERE id=?
        """;

    public static final String UPDATE_SOF_LINK = """
        UPDATE stackoverflow_links
        SET answer_count=?, score=? WHERE id=?
        """;

}
