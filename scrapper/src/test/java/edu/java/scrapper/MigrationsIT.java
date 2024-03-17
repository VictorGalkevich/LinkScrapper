package edu.java.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class MigrationsIT extends IntegrationTest {
    @Test
    void testTablesExist() {
        try (Connection connection = DriverManager.getConnection(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword()
        );
             Statement statement = connection.createStatement()) {
            ResultSet tableTelegramChatExists = statement.executeQuery(
                    "SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'chats');");
            tableTelegramChatExists.next();
            assertTrue(tableTelegramChatExists.getBoolean(1));

            ResultSet tableLinkExists = statement.executeQuery(
                    "SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'links');");
            tableLinkExists.next();
            assertTrue(tableLinkExists.getBoolean(1));

            ResultSet tableAssignmentExists = statement.executeQuery(
                    "SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'assignment');");
            tableAssignmentExists.next();
            assertTrue(tableAssignmentExists.getBoolean(1));
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
