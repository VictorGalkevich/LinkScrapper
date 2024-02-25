package edu.java.bot.formatter;

import com.pengrad.telegrambot.model.request.ParseMode;

public interface Formatter {
    String bold(String str);

    String italic(String str);

    String code(String msg, ProgrammingLanguage lang);

    ParseMode parseMode();

    enum ProgrammingLanguage {
        CPP,
        JAVA,
        GO;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
