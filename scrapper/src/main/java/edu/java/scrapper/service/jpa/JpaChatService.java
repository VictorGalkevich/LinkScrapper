package edu.java.scrapper.service.jpa;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.exception.ChatAlreadyRegisteredException;
import edu.java.scrapper.exception.ChatIsNotRegisteredException;
import edu.java.scrapper.repository.jpa.JpaChatRepository;
import edu.java.scrapper.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository repository;

    @Override
    @Transactional
    public void registerChat(Long id) {
        repository.findById(id).ifPresentOrElse(
            v -> {
                throw new ChatAlreadyRegisteredException(id);
            },
            () -> repository.save(Chat.builder()
                .id(id)
                .createdAt(OffsetDateTime.now())
                .build())
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.findById(id).ifPresentOrElse(
            repository::delete,
            () -> {
                throw new ChatIsNotRegisteredException(id);
            }
        );
    }

    @Override
    public List<Long> findAllChatsByLinkId(Long id) {
        return repository.findAllChatIdsByLinkId(id);
    }
}
