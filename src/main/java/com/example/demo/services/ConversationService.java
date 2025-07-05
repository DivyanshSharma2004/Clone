package com.example.demo.services;


import com.example.demo.model.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ConversationRepository;

/**
 * Service class for handling operations related to conversations.
 * This service interacts with the ConversationRepository to persist and retrieve Conversation data.
 */
@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    /**
     * Saves a given Conversation object to the repository.
     *
     * @param conversation The Conversation object to be saved.
     * @return The saved Conversation object.
     */
    public Conversation saveApplication(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    /**
     * Retrieves a Conversation object from the repository based on its ID.
     *
     * @param id The unique ID of the Conversation to retrieve.
     * @return The Conversation object if found, or null if not found.
     */
    public Conversation getApplicationById(String id) {
        return conversationRepository.findById(id).orElse(null);
    }

}
