package edu.java.scrapper.service.jooq;

import edu.java.scrapper.annotation.TransactionalService;
import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.exception.ChatAlreadyRegisteredException;
import edu.java.scrapper.exception.ChatIsNotRegisteredException;
import edu.java.scrapper.repository.jooq.JooqChatRepository;
import edu.java.scrapper.service.ChatService;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

@TransactionalService
@RequiredArgsConstructor
public class JooqChatService implements ChatService {
    private final JooqChatRepository chatRepository;

    @Override
    @Transactional
    public void registerChat(Long id) {
        try {
            chatRepository.save(
                Chat.builder()
                    .id(id)
                    .createdAt(OffsetDateTime.now())
                    .build()
            );
        } catch (DuplicateKeyException ignored) {
            throw new ChatAlreadyRegisteredException(id);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            chatRepository.delete(
                Chat.builder()
                    .id(id)
                    .build()
            );
        } catch (EmptyResultDataAccessException ignored) {
            throw new ChatIsNotRegisteredException(id);
        }
    }

    @Override
    public List<Long> findAllChatsByLinkId(Long id) {
        return chatRepository.findAllChatsByLinkId(id);
    }
}
