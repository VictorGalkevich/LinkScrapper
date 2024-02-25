package edu.java.bot.tgbot.request;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.AbstractSendRequest;

public class SendMessage extends AbstractSendRequest<SendMessage> {
    private static final String TEXT = "text";

    public SendMessage(Object chatId, String text) {
        super(chatId);
        this.add(TEXT, text);
    }

    public SendMessage parseMode(ParseMode parseMode) {
        return this.add("parse_mode", parseMode.name());
    }

    public String text() {
        return (String) this.getParameters().get(TEXT);
    }
}
