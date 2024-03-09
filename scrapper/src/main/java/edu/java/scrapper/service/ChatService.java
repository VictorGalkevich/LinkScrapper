package edu.java.scrapper.service;

import edu.java.scrapper.entity.Chat;
import edu.java.scrapper.exception.ChatAlreadyRegisteredException;
import edu.java.scrapper.exception.ChatIsNotRegisteredException;
import edu.java.scrapper.repository.ChatRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void registerChat(Long id) {
        chatRepository.findById(id).ifPresent(
            it -> {
                throw new ChatAlreadyRegisteredException(id);
            }
        );
        Chat chat = Chat.builder()
            .id(id)
            .links(new ArrayList<>())
            .build();
        chatRepository.save(chat);
    }

    public void delete(Long id) {
        chatRepository.delete(
            chatRepository.findById(id)
                .orElseThrow(() -> new ChatIsNotRegisteredException(id))
        );
    }
}
