package edu.java.bot.processor;

import edu.java.bot.command.Command;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;

public abstract class UserMessageProcessor {
    public abstract SendMessage process(Command command, BotUpdate update);
}
