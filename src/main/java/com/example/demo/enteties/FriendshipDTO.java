package com.example.demo.enteties;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class FriendshipDTO {

    private UUID id;
    private UUID friendId;
    private String friendUsername;
    private String friendProfilePictureUrl;
    private UUID conversationId;
    public static FriendshipDTO createFriendshipDTO(Friendship friendship, UUID currentUserId) {
        FriendshipDTO dto = new FriendshipDTO();

        UserProfile user1 = friendship.getUser();
        UserProfile user2 = friendship.getFriend();
        UUID conversationId = friendship.getConversation().getId();
        UserProfile friendProfile;
        if (user1.getId().equals(currentUserId)) {
            // if the current user is user1 then user2 is the friend
            friendProfile = user2;
        } else {
            // else user1 is the friend
            friendProfile = user1;
        }
        // fill the dto
        dto.setId(friendship.getId());
        dto.setFriendId(friendProfile.getId());
        dto.setFriendUsername(friendProfile.getName());
        dto.setFriendProfilePictureUrl(friendProfile.getProfilePic());
        dto.setConversationId(conversationId);
        return dto;
    }
}
