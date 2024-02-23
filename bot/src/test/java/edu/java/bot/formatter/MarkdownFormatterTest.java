package edu.java.bot.formatter;

import org.junit.jupiter.api.BeforeEach;

public class MarkdownFormatterTest extends FormatterTest {
    @BeforeEach
    void init() {
        formatter = new MarkdownFormatter();
        bold = "**" + TEST_STRING + "**";
        italic = "__" + TEST_STRING + "__";
        code = "```cpp\n" + TEST_STRING + "\n```\n";
    }
}
