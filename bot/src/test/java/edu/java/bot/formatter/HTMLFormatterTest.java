package edu.java.bot.formatter;

import com.pengrad.telegrambot.model.request.ParseMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTMLFormatterTest extends FormatterTest{
    @BeforeEach
    void init() {
        formatter = new HTMLFormatter();
        bold = "<b>" + TEST_STRING + "</b>";
        italic = "<i>" + TEST_STRING + "</i>";
        code = "<pre language=\"cpp\">" + TEST_STRING + "</pre>";
    }

    @Test
    void testParseMode() {
        assertEquals(formatter.parseMode(), ParseMode.HTML);
    }
}
