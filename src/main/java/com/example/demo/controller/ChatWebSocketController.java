package com.example.demo.controller;

import com.example.demo.enteties.ChatMessage;
import com.example.demo.services.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
public class ChatWebSocketController {

    private final ChatMessageService chatMessageService;

    public ChatWebSocketController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // Handle incoming WebSocket messages at /app/sendMessage
    @MessageMapping("/sendMessage")
    @SendTo("/topic/friendship/{friendshipId}") // broadcast to the friendship topic
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        message.setTimestamp(Instant.now());
        return chatMessageService.saveMessage(message);
    }
}
