package com.example.demo.repository;

import com.example.demo.enteties.Match;
import com.example.demo.enteties.UserProfile;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

//Match repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    //if potential match exists in users match rows
    boolean existsByUser1AndUser2(UserProfile user1, UserProfile user2);
    //if user exists in potential matches rows
    boolean existsByUser2AndUser1(UserProfile user2, UserProfile user1);

}
