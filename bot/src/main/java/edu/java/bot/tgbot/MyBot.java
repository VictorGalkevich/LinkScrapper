package edu.java.bot.tgbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import edu.java.bot.configuration.ApplicationConfig;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import static com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;

@Component
@Slf4j
public class MyBot extends TelegramBot {
    private final List<Command> commands;

    public MyBot(ApplicationConfig conf, List<Command> commands) {
        super(conf.telegramToken());
        this.commands = commands;
        this.setUpdatesListener(this::process, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            } else {
                log.error(e.getLocalizedMessage());
            }
        });
        execute(setUpMenuCommands());
    }

    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() != null) {
                execute(preProcess(update));
            }
        }
        return CONFIRMED_UPDATES_ALL;
    }

    private SendMessage preProcess(Update update) {
        SendMessage message;
        Command cmd = retrieveCommand(update);
        if (cmd != null) {
            message = cmd.handle(update);
        } else {
            message = new SendMessage(
                update.message().chat().id(),
                "Sorry, I can't proceed this type of message. \nAvailable commands: /help"
            );
        }
        return message;
    }

    private Command retrieveCommand(Update update) {
        String text = update.message().text();
        Command command = null;
        for (Command value : commands) {
            if (text.startsWith(value.command())) {
                command = value;
                break;
            }
        }
        return command;
    }

    private SetMyCommands setUpMenuCommands() {
        return new SetMyCommands(commands.stream()
            .map(Command::toApiCommand)
            .toArray(BotCommand[]::new));
    }
}
