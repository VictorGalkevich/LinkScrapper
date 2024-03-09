package edu.java.bot.formatter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class FormatterTest {
    protected Formatter formatter;
    protected static final String TEST_STRING = "test";
    protected String bold;
    protected String italic;
    protected String code;

    @Test
    void testBold() {
        final String expected = bold;
        final String actual = formatter.bold(TEST_STRING);
        assertEquals(expected, actual);
    }

    @Test
    void testItalic() {
        final String expected = italic;
        final String actual = formatter.italic(TEST_STRING);
        assertEquals(expected, actual);
    }

    @Test
    void testCode() {
        final String expected = code;
        final String actual = formatter.code(TEST_STRING, Formatter.ProgrammingLanguage.CPP);
        assertEquals(expected, actual);
    }
}
