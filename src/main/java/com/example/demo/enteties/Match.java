package com.example.demo.enteties;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.Instant;

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

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UserProfile getUser1() { return user1; }
    public void setUser1(UserProfile user1) { this.user1 = user1; }

    public UserProfile getUser2() { return user2; }
    public void setUser2(UserProfile user2) { this.user2 = user2; }

    public Instant getMatchedAt() { return matchedAt; }
    public void setMatchedAt(Instant matchedAt) { this.matchedAt = matchedAt; }

    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }

    public enum MatchStatus {
        PENDING,
        ACTIVE,
        BLOCKED,
        ENDED
    }
}

