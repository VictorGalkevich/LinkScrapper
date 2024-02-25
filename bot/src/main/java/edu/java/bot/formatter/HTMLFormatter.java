package edu.java.bot.formatter;

import com.pengrad.telegrambot.model.request.ParseMode;

public class HTMLFormatter implements Formatter {
    @Override
    public String bold(String str) {
        return "<b>" + str + "</b>";
    }

    @Override
    public String italic(String str) {
        return "<i>" + str + "</i>";
    }

    @Override
    public ParseMode parseMode() {
        return ParseMode.HTML;
    }

    @Override
    public String code(String msg, ProgrammingLanguage lang) {
        return "<pre language=\"%s\">%s</pre>".formatted(lang, msg);
    }
}
