package edu.java.bot.repository;

import edu.java.bot.entity.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserRepository implements Repository<User, Long> {
    private static final Map<Long, User> MAP = new HashMap<>();

    @Override
    public User save(User obj) {
        MAP.put(obj.getId(), obj);
        return MAP.get(obj.getId());
    }

    @Override
    public Optional<User> findById(Long identifier) {
        return Optional.ofNullable(MAP.get(identifier));
    }
}
