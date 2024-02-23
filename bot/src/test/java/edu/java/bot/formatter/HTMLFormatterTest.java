package edu.java.bot.formatter;

import org.junit.jupiter.api.BeforeEach;

public class HTMLFormatterTest extends FormatterTest{
    @BeforeEach
    void init() {
        formatter = new HTMLFormatter();
        bold = "<b>" + TEST_STRING + "</b>";
        italic = "<i>" + TEST_STRING + "</i>";
        code = "<pre language=\"cpp\">" + TEST_STRING + "</pre>";
    }
}
