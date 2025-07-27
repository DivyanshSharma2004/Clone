package com.example.demo.repository;

import com.example.demo.enteties.Match;
import com.example.demo.enteties.UserProfile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//Match repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    //if potential match exists in users match rows
    boolean existsByUser1AndUser2(UserProfile user1, UserProfile user2);

    Optional<Match> findById(UUID id);

    Optional<Match> findByUser1IdAndUser2Id(UUID user1Id, UUID user2Id);
//    (ai generated)
    @Query("""
    SELECT m FROM Match m
    WHERE (m.user1.id = :profileId OR m.user2.id = :profileId)
      AND m.status = :status
      AND m.requester.id <> :profileId
""")
    List<Match> findPendingRequestsForUser(
            @Param("profileId") UUID profileId,
            @Param("status") Match.MatchStatus status
    );

}
