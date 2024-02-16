package edu.java.bot.util;

import edu.java.bot.entity.Link;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static edu.java.bot.util.LinkUtil.*;

public class LinkUtilTest {

    @Test
    void testParseWithSlashPositive() {
        String text = "http://google.com/";
        Link expected = Link.builder()
            .uri(text)
            .build();
        assertEquals(expected, parse(text));
    }

    @Test
    void testParseWithoutSlashPositive() {
        String text = "http://google.com";
        Link expected = Link.builder()
            .uri(text + "/")
            .build();
        assertEquals(expected, parse(text));
    }

    @Test
    void testValidationHttpPositive() {
        String text = "http://google.com";
        assertTrue(isValid(text));
    }

    @Test
    void testValidationHttpsPositive() {
        String text = "https://google.com";
        assertTrue(isValid(text));
    }

    @Test
    void testValidationWithoutProtocolPositive() {
        String text = "google.com";
        assertTrue(isValid(text));
    }

    @Test
    void testValidationWrongSecondLevelDomainNegative() {
        String text = "http://goo gle.com";
        assertFalse(isValid(text));
    }

    @Test
    void testValidationWrongFirstLevelDomainNegative() {
        String text = "http://google.c om";
        assertFalse(isValid(text));
    }

    @Test
    void testValidationMissingFirstLevelDomainNegative() {
        String text = "http://google";
        assertFalse(isValid(text));
    }

    @Test
    void testValidationThirdLevelDomainPositive() {
        String text = "https://gist.google.com";
        assertTrue(isValid(text));
    }

    @Test
    void testValidationСontextPathPositive() {
        String text = "https://gist.google.com/api";
        assertTrue(isValid(text));
    }

    @Test
    void testValidationParamsPositive() {
        String text = "https://gist.google.com?page=1";
        assertTrue(isValid(text));
    }

    @Test
    void testValidationContextPathNegative() {
        String text = "https://gist.google.com/ a p i";
        assertFalse(isValid(text));
    }
}
