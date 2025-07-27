package com.example.demo.repository;

import com.example.demo.enteties.Friendship;
import com.example.demo.enteties.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface FriendshipRepository extends JpaRepository<Friendship, UUID> {


    //use to avoid lazy-instalization coded by chatgpt to debug this query^
    @Query("SELECT f FROM Friendship f " +
            "JOIN FETCH f.user " +
            "JOIN FETCH f.friend " +
            "WHERE f.user.id = :id OR f.friend.id = :id")
    List<Friendship> findFriendshipsWithUsers(@Param("id") UUID id);

    /**
     * Deletes a friendship between two users.
     *(AI-Generated Documentation)
     * @param user   The first user.
     * @param friend The second user.
     */
    void deleteByUserAndFriend(UserProfile user, UserProfile friend);

    /**
     * Checks if a friendship exists between two users in either direction.
     *(AI-Generated Documentation)
     * @param user1 The first user.
     * @param user2 The second user.
     * @return true if a friendship exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM Friendship f WHERE (f.user = :user1 AND f.friend = :user2) OR (f.user = :user2 AND f.friend = :user1)")
    boolean existsBetween(UserProfile user1, UserProfile user2);

    /**
     * Checks if a given user is part of a specific friendship.
     *(AI-Generated Documentation)
     * @param userId       The ID of the user.
     * @param friendshipId The ID of the friendship.
     * @return true if the user belongs to the friendship, false otherwise.
     */
    @Query("""
    SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
    FROM Friendship f
    WHERE f.id = :friendshipId AND (f.user.id = :userId OR f.friend.id = :userId)
""")
    boolean isUserInFriendship(@Param("userId") UUID userId, @Param("friendshipId") UUID friendshipId);

}
