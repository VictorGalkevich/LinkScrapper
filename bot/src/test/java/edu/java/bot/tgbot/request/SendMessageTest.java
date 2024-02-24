package edu.java.bot.tgbot.request;

import com.pengrad.telegrambot.model.request.ParseMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SendMessageTest {
    private static SendMessage sendMessage;
    private static final String MOCKED_TEXT = "test";
    private static final Long MOCKED_USER_ID = 123L;

    @BeforeAll
    public static void init() {
        sendMessage = new SendMessage(MOCKED_USER_ID, MOCKED_TEXT);
        sendMessage.parseMode(ParseMode.HTML);
    }

    @Test
    void testReceiver() {
        Assertions.assertEquals(MOCKED_USER_ID, sendMessage.getParameters().get("chat_id"));
    }

    @Test
    void testMessage() {
        Assertions.assertEquals(MOCKED_TEXT, sendMessage.text());
    }

    @Test
    void testParseMode() {
        Assertions.assertEquals(ParseMode.HTML.name(), sendMessage.getParameters().get("parse_mode"));
    }
}
