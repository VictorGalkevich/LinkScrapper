package edu.java.bot.formatter;

import com.pengrad.telegrambot.model.request.ParseMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkdownFormatterTest extends FormatterTest {
    @BeforeEach
    void init() {
        formatter = new MarkdownFormatter();
        bold = "**" + TEST_STRING + "**";
        italic = "__" + TEST_STRING + "__";
        code = "```cpp\n" + TEST_STRING + "\n```\n";
    }

    @Test
    void testParseMode() {
        assertEquals(formatter.parseMode(), ParseMode.MarkdownV2);
    }
}
