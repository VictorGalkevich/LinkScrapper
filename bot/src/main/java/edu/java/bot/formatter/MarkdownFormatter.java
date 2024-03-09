package edu.java.bot.formatter;

import com.pengrad.telegrambot.model.request.ParseMode;

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
    public ParseMode parseMode() {
        return ParseMode.MarkdownV2;
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
