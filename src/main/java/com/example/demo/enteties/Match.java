package com.example.demo.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    private UserProfile user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false)
    private UserProfile user2;

    @Column(nullable = false)
    private Instant matchedAt;

    @Enumerated(EnumType.STRING)
    private MatchStatus status; // e.g., PENDING, ACTIVE, BLOCKED, ENDED

    // Constructors, getters, setters
    public Match() {}

    public enum MatchStatus {
        PENDING,
        ACTIVE,
        BLOCKED,
        ENDED
    }
}

