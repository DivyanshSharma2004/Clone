package com.example.demo.config;

import com.example.demo.services.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.UUID;
//made with help of ai
@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    //friendship service
    @Autowired
    private final FriendshipService friendshipService;

    public WebSocketAuthInterceptor(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        //check recieving and sending messages
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {
            //check usersession make sure they are authenticated
            UUID userId = (UUID) accessor.getSessionAttributes().get("profileId");
            if (userId == null) {
                throw new IllegalArgumentException("Not authenticated");
            }

            // Extract friendshipId from destination
            String destination = accessor.getDestination();
            if (destination != null && destination.contains("/conversation/")) {
                String[] parts = destination.split("/");
                UUID conversationId = UUID.fromString(parts[parts.length - 1]);
                if (!friendshipService.isUserInConversation(userId, conversationId)) {
                    throw new IllegalArgumentException("You are not allowed to message this person");
                }
            }
        }

        return message;
    }
}
