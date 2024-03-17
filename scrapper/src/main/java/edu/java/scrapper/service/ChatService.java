package edu.java.scrapper.service;

import java.util.List;

public interface ChatService {
    void registerChat(Long id);

    void delete(Long id);

    List<Long> findAllChatsByLinkId(Long id);
}
