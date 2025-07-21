package com.example.demo.enteties;

import com.example.demo.enteties.UserProfile;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserProfile sender;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    private Instant sentAt;

    // Constructors, getters, setters
    public Message() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }

    public UserProfile getSender() { return sender; }
    public void setSender(UserProfile sender) { this.sender = sender; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Instant getSentAt() { return sentAt; }
    public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
}