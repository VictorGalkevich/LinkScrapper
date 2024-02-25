package edu.java.bot.mapper;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.tgbot.model.BotUpdate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UpdateMapper implements Mapper<Update, BotUpdate> {
    @Override
    public BotUpdate map(Update from) {
        return new BotUpdate(from);
    }

    public List<BotUpdate> mapToList(List<Update> from) {
        return from.stream()
            .map(this::map)
            .toList();
    }
}
