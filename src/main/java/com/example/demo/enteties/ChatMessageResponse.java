package com.example.demo.enteties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
//what server sends to user recieving
public class ChatMessageResponse {
    private UUID id;
    private UUID conversationID;
    private String senderName;
    private String content;
    private Instant timestamp;

    public ChatMessageResponse(UUID id, UUID conversationID, String senderName, String content, Instant timestamp) {
        this.id = id;
        this.conversationID = conversationID;
        this.senderName = senderName;
        this.content = content;
        this.timestamp = timestamp;
    }
}

