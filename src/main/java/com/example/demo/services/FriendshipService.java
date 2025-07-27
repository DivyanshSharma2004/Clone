package com.example.demo.services;

import com.example.demo.enteties.Friendship;
import com.example.demo.enteties.FriendshipDTO;
import com.example.demo.enteties.UserProfile;
import com.example.demo.repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    /**
     * Retrieves all friendships for a given user.
     *(AI-Generated Documentation)
     * @param currentUserId The ID of the current user.
     * @return A list of FriendshipDTO objects.
     */
    public List<FriendshipDTO> getFriendshipsForUser(UUID currentUserId) {
        List<Friendship> friendships = friendshipRepository.findFriendshipsWithUsers(currentUserId);
        return friendships.stream()
                .map(f -> FriendshipDTO.createFriendshipDTO(f, currentUserId))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether a user is part of a specific friendship.
     *(AI-Generated Documentation)
     * @param userId       The ID of the user.
     * @param friendshipId The ID of the friendship.
     * @return true if the user is in the friendship, false otherwise.
     */
    public boolean isUserInFriendship(UUID userId, UUID friendshipId) {
        return friendshipRepository.isUserInFriendship(userId, friendshipId);
    }


    /**
     * Removes a bidirectional friendship between two users.
     *(AI-Generated Documentation)
     * @param user1 The first user.
     * @param user2 The second user.
     */
    public void removeFriendship(UserProfile user1, UserProfile user2) {
        friendshipRepository.deleteByUserAndFriend(user1, user2);
        friendshipRepository.deleteByUserAndFriend(user2, user1);
    }
}

