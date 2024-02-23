package edu.java.bot.processor;

import edu.java.bot.command.Command;
import edu.java.bot.command.StartCommand;
import edu.java.bot.entity.User;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.tgbot.model.BotUpdate;
import edu.java.bot.tgbot.request.SendMessage;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartMessageProcessor extends UserMessageProcessor {
    private final UserRepository userRepository;

    @Override
    public SendMessage process(Command command, BotUpdate update) {
        if (command instanceof StartCommand) {
            Long id = update.id();
            if (userRepository.findById(id).isEmpty()) {
                User user = User.builder().id(id).links(new ArrayList<>()).build();
                userRepository.save(user);
            }
            return new SendMessage(id, "Welcome, %s, use /help to see the help page".formatted(
                    update.username()
            ));
        } else {
            return null;
        }
    }
}
