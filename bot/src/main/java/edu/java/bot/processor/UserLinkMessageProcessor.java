package edu.java.bot.processor;

import edu.java.bot.command.Command;
import edu.java.bot.tgbot.model.BotUpdate;
import java.net.URI;
import static edu.java.bot.util.LinkUtil.parse;

public abstract class UserLinkMessageProcessor extends UserMessageProcessor {

    public URI retrieveLink(BotUpdate update) {
        String[] s = update.text().split(" ");
        if (s.length != 2) {
            throw new RuntimeException("%s syntax might be [%s <link>]");
        }
        URI link = parse(s[1]);
        if (link == null) {
            throw new RuntimeException("Provided link is invalid");
        }
        return link;
    }

    public String getMessage(Exception e, Command command) {
        if (e.getMessage().charAt(0) == '%') {
            return String.format(e.getMessage(), command.command(), command.command());
        }
        return e.getMessage();
    }
}
