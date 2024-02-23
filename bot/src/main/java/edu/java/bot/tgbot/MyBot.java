package edu.java.bot.tgbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import edu.java.bot.command.UnknownCommand;
import edu.java.bot.mapper.UpdateMapper;
import edu.java.bot.processor.UserMessageProcessor;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyBot implements Bot {
    private final List<Command> commands;
    private final List<UserMessageProcessor> processors;
    private final TelegramBot bot;
    private final UpdateMapper mapper;

    @Override
    @PostConstruct
    public void start() {
        bot.setUpdatesListener(this);
        bot.execute(setUpMenuCommands());
    }

    @Override
    public int process(List<Update> list) {
        List<BotUpdate> botUpdates = mapper.mapToList(list);
        for (BotUpdate update : botUpdates) {
            if (!update.isMessageNull()) {
                bot.execute(processUpdate(update));
            }
        }
        return CONFIRMED_UPDATES_ALL;
    }

    private SendMessage processUpdate(BotUpdate update) {
        Command cmd = retrieveCommand(update);
        return processors.stream()
            .map(x -> x.process(cmd, update))
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow();
    }

    private Command retrieveCommand(BotUpdate update) {
        String text = update.text();
        Command command = new UnknownCommand();
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
