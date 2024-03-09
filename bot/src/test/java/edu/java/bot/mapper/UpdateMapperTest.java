package edu.java.bot.mapper;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.tgbot.model.BotUpdate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateMapperTest {
    private UpdateMapper mapper;

    @BeforeEach
    void init() {
        mapper = new UpdateMapper();
    }

    @Test
    void testMappingPositive() {
        Update update = new Update();
        BotUpdate bupdate = mapper.map(update);
        assertThat(bupdate).hasFieldOrPropertyWithValue("update", update);
    }

    @Test
    void testMappingNullPositive() {
        Update update = null;
        BotUpdate bupdate = mapper.map(update);
        assertThat(bupdate).hasFieldOrPropertyWithValue("update", update);
    }

    @Test
    void testMappingListNullPositive() {
        Update update = null;
        List<Update> list = new ArrayList<>();
        list.add(update);
        list.add(update);
        List<BotUpdate> bupdates = mapper.mapToList(list);
        assertThat(bupdates).hasSize(2);
        for (BotUpdate bupdate : bupdates) {
            assertThat(bupdate).hasFieldOrPropertyWithValue("update", update);
        }
    }

    @Test
    void testMappingListPositive() {
        Update update = new Update();
        List<Update> list = List.of(update, update, update);
        List<BotUpdate> bupdates = mapper.mapToList(list);
        assertThat(bupdates).hasSize(3);
        for (BotUpdate bupdate : bupdates) {
            assertThat(bupdate).hasFieldOrPropertyWithValue("update", update);
        }
    }
}
