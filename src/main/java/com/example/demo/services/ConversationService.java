package com.example.demo.services;

import com.example.demo.enteties.Conversation;
import com.example.demo.enteties.UserProfile;
import com.example.demo.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public Conversation getOrCreate1on1(UserProfile user1, UserProfile user2) {
        return conversationRepository.find1on1Conversation(user1.getId(), user2.getId())
                .orElseGet(() -> conversationRepository.save(Conversation.create1on1(user1, user2)));
    }
}
