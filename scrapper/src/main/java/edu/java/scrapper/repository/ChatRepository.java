package edu.java.scrapper.repository;

import edu.java.scrapper.entity.Chat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class ChatRepository implements Repository<Chat, Long> {
    private static final Map<Long, Chat> MAP = new HashMap<>();

    @Override
    public Chat save(Chat obj) {
        MAP.put(obj.getId(), obj);
        return MAP.get(obj.getId());
    }

    @Override
    public Optional<Chat> findById(Long identifier) {
        return Optional.ofNullable(MAP.get(identifier));
    }

    @Override
    public void delete(Chat object) {
        MAP.remove(object.getId());
    }
}
