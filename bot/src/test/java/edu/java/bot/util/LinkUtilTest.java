package edu.java.bot.util;

import java.net.URI;
import org.junit.jupiter.api.Test;
import static edu.java.bot.util.LinkUtil.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LinkUtilTest {

    @Test
    void testParseWithSlashPositive() {
        String text = "http://google.com/";
        URI expected = URI.create(text);
        assertEquals(expected, parse(text));
    }

    @Test
    void testParseWithoutSlashPositive() {
        String text = "http://google.com/";
        URI expected = URI.create(text);
        assertEquals(expected, parse(text));
    }

    @Test
    void testValidationHttpPositive() {
        String text = "http://google.com";
        assertNotNull(parse(text));
    }

    @Test
    void testValidationHttpsPositive() {
        String text = "https://google.com";
        assertNotNull(parse(text));
    }

    @Test
    void testValidationWrongSecondLevelDomainNegative() {
        String text = "http://goo gle.com";
        assertNull(parse(text));
    }

    @Test
    void testValidationWrongFirstLevelDomainNegative() {
        String text = "http://google.c om";
        assertNull(parse(text));
    }

    @Test
    void testValidationThirdLevelDomainPositive() {
        String text = "https://gist.google.com";
        assertNotNull(parse(text));
    }

    @Test
    void testValidationContextPathPositive() {
        String text = "https://gist.google.com/api";
        assertNotNull(parse(text));
    }

    @Test
    void testValidationParamsPositive() {
        String text = "https://gist.google.com?page=1";
        assertNotNull(parse(text));
    }
}
