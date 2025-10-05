package com.example.demo.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "friendships",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "friend_id"}))
public class Friendship {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private UserProfile friend;

    private Instant createdAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;


    public Friendship() {}

    public Friendship(UserProfile user, UserProfile friend, Conversation conversation) {
        this.user = user;
        this.friend = friend;
        this.conversation = conversation;
    }
}

