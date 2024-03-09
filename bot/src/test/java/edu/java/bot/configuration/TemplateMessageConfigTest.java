package edu.java.bot.configuration;

import edu.java.bot.formatter.Formatter;
import edu.java.bot.formatter.MarkdownFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TemplateMessageConfigTest {
    private TemplateMessageConfig config;
    private Formatter formatter;

    @BeforeEach
    void init() {
        formatter = new MarkdownFormatter();
        config = new TemplateMessageConfig(formatter);
    }

    @Test
    void testHelpString() {
        final String expected = formatter.bold("%s") + " - %s\n";
        final String actual = config.help();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testListString() {
        final String expected = formatter.bold("%s") + " - %s\n";
        final String actual = config.help();
        Assertions.assertEquals(expected, actual);
    }
}
