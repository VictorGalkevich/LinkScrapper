package edu.java.bot.tgbot.model;

import com.pengrad.telegrambot.model.Update;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BotUpdate {
    private final Update update;

    public Long id() {
        return update.message().chat().id();
    }

    public String username() {
        return update.message().chat().username();
    }

    public String text() {
        return update.message().text();
    }

    public boolean isMessageNull() {
        return update.message() == null;
    }
}
