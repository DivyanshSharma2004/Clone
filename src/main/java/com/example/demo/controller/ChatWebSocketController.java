package com.example.demo.controller;

import com.example.demo.enteties.*;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.repository.FriendshipRepository;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.services.ChatMessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;


import java.time.Instant;
import java.util.UUID;

@Controller
public class ChatWebSocketController {
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    FriendshipRepository friendshipRepository;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate; // <-- inject this

    public ChatWebSocketController(ChatMessageService chatMessageService, SimpMessagingTemplate messagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatMessageRequest messageRequest) {

        UUID senderId = (UUID) httpSession.getAttribute("profileId");
        UserProfile sender = userProfileRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Conversation conversation = conversationRepository.findById(messageRequest.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        // check sender is participant
        if (!conversation.hasParticipant(senderId)) {
            throw new RuntimeException("Sender is not part of this conversation");
        }

        // check friendship exists
        boolean friendshipExists = friendshipRepository.existsByConversation_IdAndUser_Id(
                conversation.getId(), senderId);
        if (!friendshipExists) {
            throw new RuntimeException("Cannot send message, friendship no longer exists");//so conversation object exists from previous friendship but after they broke up doesnt exist anylonger.
        }

        // determine receiver
        UUID receiverId = conversation.getParticipants().stream()
                .filter(u -> !u.getId().equals(senderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No other person found"))
                .getId();

        // create message entity
        ChatMessage message = new ChatMessage();
        message.setConversation(conversation);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(messageRequest.getContent());
        message.setTimestamp(Instant.now());

        chatMessageService.saveMessage(message);

        // convert to response dto, pair it up so front-end knows the name of who they are. so they can display the messages properly
        ChatMessageResponse response = new ChatMessageResponse(
                message.getId(),
                message.getConversation().getId(),
                sender.getName(),
                message.getContent(),
                message.getTimestamp()
        );

        messagingTemplate.convertAndSend("/topic/conversation/" + conversation.getId(), response);
    }
}

