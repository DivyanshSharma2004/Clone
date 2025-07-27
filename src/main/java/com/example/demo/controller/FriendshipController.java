package com.example.demo.controller;

import com.example.demo.enteties.ChatMessage;
import com.example.demo.enteties.ChatMessageDTO;
import com.example.demo.enteties.FriendshipDTO;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.services.FriendshipService;
import com.example.demo.services.ChatMessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/message")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private HttpSession httpSession;

    /**
     * Displays the messaging page (Thymeleaf view).
     *(AI-Generated Documentation)
     * @return The path to the private messages page.
     */
    @GetMapping("/home")
    public String messagingPage() {
        return "/private/messages"; // Thymeleaf view
    }

    /**
     * Retrieves a list of friendships for the currently logged-in user.
     *(AI-Generated Documentation)
     * @return A ResponseEntity containing a list of FriendshipDTO objects.
     */
    @GetMapping("/friendships")
    @ResponseBody
    public ResponseEntity<List<FriendshipDTO>> getFriendships() {
        UUID profileId = (UUID) httpSession.getAttribute("profileId");
        List<FriendshipDTO> friendships = friendshipService.getFriendshipsForUser(profileId);
        return ResponseEntity.ok(friendships);
    }

    /**
     * Retrieves all chat messages associated with a given friendship.
     *(AI-Generated Documentation)
     * @param friendshipId The ID of the friendship.
     * @return A ResponseEntity containing a list of ChatMessage objects.
     */
    @GetMapping("/friendship/{friendshipId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesByFriendship(@PathVariable UUID friendshipId) {
        List<ChatMessage> messages = chatMessageService.getMessagesByFriendshipId(friendshipId);
        List<ChatMessageDTO> dtos = messages.stream()
                .map(ChatMessageDTO::createMessageDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Sends a chat message to a specific friendship conversation.
     * (AI-Generated Documentation)
     * <p>
     * This method also broadcasts the message to all subscribers of the friendship topic.
     *
     * @param friendshipId The ID of the friendship.
     * @param message      The chat message to send.
     */
    @MessageMapping("/chat/{friendshipId}")
    public void sendMessage(@DestinationVariable UUID friendshipId, ChatMessage message) {
        message.setTimestamp(Instant.now());
        chatMessageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/friendship/" + friendshipId, message);
    }
}