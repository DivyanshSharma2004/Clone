package com.example.demo.repository;

import com.example.demo.enteties.User;
import com.example.demo.enteties.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for accessing and managing UserProfile entities.
 *
 * Provides a method to fetch a profile by the associated user.
 * ai-generated comment
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Find a profile by the given user.
     *ai-generated comment
     *
     * @param user the user to search by
     * @return an Optional containing the profile, if found
     */
    Optional<UserProfile> findByUser(User user);
}
