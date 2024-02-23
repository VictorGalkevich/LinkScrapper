package edu.java.bot.formatter;

public class MarkdownFormatter implements Formatter {

    @Override
    public String bold(String str) {
        return "**" + str + "**";
    }

    @Override
    public String italic(String str) {
        return "__" + str + "__";
    }

    @Override
    public String code(String msg, ProgrammingLanguage lang) {
        return """
                ```%s
                %s
                ```
                """.formatted(lang, msg);
    }
}
