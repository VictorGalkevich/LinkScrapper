package edu.java.bot.processor;

import edu.java.bot.command.Command;
import edu.java.bot.command.UnknownCommand;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UnknownMessageProcessor extends UserMessageProcessor {

    @Override
    public SendMessage process(Command command, BotUpdate update) {
        if (command instanceof UnknownCommand) {
            return new SendMessage(
                update.id(),
                "Sorry, I can't proceed this type of message. \nAvailable commands: /help"
            );
        } else  {
            return null;
        }
    }
}
