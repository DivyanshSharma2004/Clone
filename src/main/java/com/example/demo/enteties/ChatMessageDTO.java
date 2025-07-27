package com.example.demo.enteties;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Getter
@Setter
public class ChatMessageDTO {

    private UUID id;
    private UUID friendshipId;
    private UUID senderId;
    private String content;
    private Instant timestamp;

    // Getters and setters

    public static ChatMessageDTO createMessageDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(message.getId());
        dto.setFriendshipId(message.getFriendship().getId());
        dto.setSenderId(message.getSenderId());       // direct UUID from field
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }

}
