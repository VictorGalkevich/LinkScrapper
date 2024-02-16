package edu.java.bot.repository;

import edu.java.bot.entity.User;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class UserRepository implements Repository<User, Long> {
    private static final Map<Long, User> map = new HashMap<>();

    @Override
    public User save(User obj) {
        map.put(obj.getId(), obj);
        return map.get(obj.getId());
    }

    @Override
    public Optional<User> findById(Long identifier) {
        return Optional.ofNullable(map.get(identifier));
    }
}
