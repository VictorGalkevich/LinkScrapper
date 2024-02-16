package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.entity.User;
import edu.java.bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class StartMessageProcessor implements UserMessageProcessor {
    private final UserRepository userRepository;

    @Override
    public SendMessage process(Update update) {
        Long id = update.message().chat().id();
        if (userRepository.findById(id).isEmpty()) {
            User user = User.builder().id(id).links(new ArrayList<>()).build();
            userRepository.save(user);
        }
        return new SendMessage(id, "Welcome, %s, use /help to see the help page".formatted(
            update.message()
            .chat()
            .username()));
    }
}
