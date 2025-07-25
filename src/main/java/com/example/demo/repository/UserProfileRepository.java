package com.example.demo.repository;

import com.example.demo.enteties.User;
import com.example.demo.enteties.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing and managing UserProfile entities.
 *
 * Provides a method to fetch a profile by the associated user.
 * ai-generated comment
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    /**
     * Find a profile by the given user.
     *ai-generated comment
     *
     * @param user the user to search by
     * @return an Optional containing the profile, if found
     */
    Optional<UserProfile> findByUser(User user);

    //generated via chatgpt
    @Query(value = """
        SELECT * FROM user_profile u
        WHERE u.id != :currentUserProfileId
        AND u.id NOT IN (
            SELECT CASE WHEN m.user1_id = :currentUserProfileId THEN m.user2_id ELSE m.user1_id END
            FROM matches m
            WHERE m.user1_id = :currentUserProfileId OR m.user2_id = :currentUserProfileId
        )
        ORDER BY RANDOM()
        LIMIT :batchSize
        """, nativeQuery = true)
    List<UserProfile> findUnmatchedRandomBatch(@Param("currentUserProfileId") UUID currentUserProfileId,
                                               @Param("batchSize") int batchSize);
}
