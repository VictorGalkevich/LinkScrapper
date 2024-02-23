package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;

public interface Command {

    String command();

    String description();

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
