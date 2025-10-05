package com.example.demo.services;

import com.example.demo.enteties.*;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.repository.FriendshipRepository;
import com.example.demo.repository.ChatMessageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ChatMessageService {
    @Autowired ChatMessageRepository chatMessageRepository;
    @Autowired FriendshipRepository friendshipRepository; // to get Friendship by id

    //get messages by conversationId
    public List<ChatMessage> getMessagesByConversationId(UUID conversationId) {
        return chatMessageRepository.findByConversation_IdOrderByTimestampAsc(conversationId);
    }

    //save message  to database
    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(Instant.now());
        return chatMessageRepository.save(message);
    }
}//todo: these all think that youre using friendshipid to get access to messages this is wrong appraoch use conversationid.


