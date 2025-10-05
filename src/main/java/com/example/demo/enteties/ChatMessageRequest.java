package com.example.demo.enteties;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
//what user sends server to send to another user
public class ChatMessageRequest {
    private UUID conversationId; // client knows conversationId
    private String content;
    private UUID friendShipId;
}
