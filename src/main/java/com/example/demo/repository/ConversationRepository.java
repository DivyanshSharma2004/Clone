package com.example.demo.repository;

import com.example.demo.enteties.Conversation;
import com.example.demo.enteties.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

//AI-generated queries i do understand the sql
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    @Query("""
    SELECT c FROM Conversation c
    JOIN c.participants p1
    JOIN c.participants p2
    WHERE (p1.id = :user1Id AND p2.id = :user2Id)
       OR (p1.id = :user2Id AND p2.id = :user1Id)
    """)
    Optional<Conversation> find1on1Conversation(@Param("user1Id") UUID user1Id,
                                                @Param("user2Id") UUID user2Id);

    @Query("""
    SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
    FROM Conversation c
    JOIN c.participants p
    WHERE c.id = :conversationId AND p.id = :userId
""")
    boolean existsByIdAndUserId(@Param("userId") UUID userId,
                                @Param("conversationId") UUID conversationId);

}

