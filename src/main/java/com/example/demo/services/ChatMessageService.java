package com.example.demo.services;

import com.example.demo.enteties.*;
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

    //get messages by friendshipID
    public List<ChatMessage> getMessagesByFriendshipId(UUID friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new EntityNotFoundException("Friendship not found"));
        return chatMessageRepository.findByFriendshipOrderByTimestampAsc(friendship);
    }
    //save message  to database
    public ChatMessage saveMessage(ChatMessage message) {
        //make sure the friendship even exits
        Friendship friendship = friendshipRepository.findById(message.getFriendship().getId())
                .orElseThrow(() -> new EntityNotFoundException("Friendship not found"));
        message.setFriendship(friendship);
        message.setTimestamp(Instant.now());
        return chatMessageRepository.save(message);
    }
}


