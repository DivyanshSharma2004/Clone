package com.example.demo.repository;

import com.example.demo.enteties.Friendship;
import com.example.demo.enteties.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByConversation_IdOrderByTimestampAsc(UUID conversationId);

}