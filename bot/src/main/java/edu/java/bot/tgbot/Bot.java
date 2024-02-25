package edu.java.bot.tgbot;

import com.pengrad.telegrambot.UpdatesListener;

public interface Bot extends UpdatesListener {
    void start();
}
