package com.example.demo.controller;

import com.example.demo.enteties.*;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.UserProfileRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
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
    private UserProfileRepository userProfileRepository;
    @Autowired
    private HttpSession httpSession;


    @GetMapping("/conversation/{conversationId}")
    @ResponseBody
    public ResponseEntity<List<ChatMessageResponse>> getMessagesByConversation(@PathVariable UUID conversationId) {
        List<ChatMessage> messages = chatMessageService.getMessagesByConversationId(conversationId);
        List<ChatMessageResponse> dtos = new ArrayList<>();
        //should use streams but wont be aproblem if i change the pagnation method
        for (ChatMessage message : messages) {
            String senderName = userProfileRepository.findById(message.getSenderId())
                    .map(UserProfile::getName)
                    .orElse("Unknown");

            ChatMessageResponse dto = new ChatMessageResponse(
                    message.getId(),
                    message.getConversation().getId(),
                    senderName,
                    message.getContent(),
                    message.getTimestamp()
            );

            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);
    }

    /**
     * Displays the messaging page (Thymeleaf view).
     *(AI-Generated Documentation)
     * @return The path to the private messages page.
     */
    @GetMapping("/message/home")
    public String messagingPage() {
        return "/private/messages"; // Thymeleaf view
    }

    /**
     * Retrieves a list of friendships for the currently logged-in user.
     *(AI-Generated Documentation)
     * @return A ResponseEntity containing a list of FriendshipDTO objects.
     */
    @GetMapping("/message/friendships")
    @ResponseBody
    public ResponseEntity<List<FriendshipDTO>> getFriendships() {
        UUID profileId = (UUID) httpSession.getAttribute("profileId");
        List<FriendshipDTO> friendships = friendshipService.getFriendshipsForUser(profileId);
        return ResponseEntity.ok(friendships);
    }

    //FIXME: currently not working
    /**
     * Removes a bidirectional friendship between the current user and a friend.
     *
     * @param friendId The UUID of the friend to remove.
     * @return ResponseEntity with success message.
     */
    @DeleteMapping("/message/friendships/{friendId}")
    @ResponseBody
    public ResponseEntity<String> removeFriendship(@PathVariable UUID friendId) {
        UUID currentProfileId = (UUID) httpSession.getAttribute("profileId");

        if (currentProfileId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        friendshipService.removeFriendshipById(currentProfileId,friendId);
        Optional<UserProfile> friendOpt=userProfileRepository.findById(friendId);
        Optional<UserProfile> userOpt= userProfileRepository.findById(currentProfileId);
        if (userOpt.isPresent()&&friendOpt.isPresent()) {
            UserProfile user = userOpt.get();
            UserProfile friend = friendOpt.get();
            friendshipService.removeFriendship(user,friend);
            friendshipService.removeFriendshipById(currentProfileId,friendId);
            return ResponseEntity.ok("Friendship removed successfully");
        } else {
            return ResponseEntity.ok("Friendship removed unsuccessfully");
        }
    }
}