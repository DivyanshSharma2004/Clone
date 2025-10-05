package com.example.demo.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "conversation_user",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserProfile> participants = new HashSet<>();

    private Instant createdAt = Instant.now();

    public static Conversation create1on1(UserProfile user1, UserProfile user2) {
        Conversation conversation = new Conversation();
        conversation.getParticipants().add(user1);
        conversation.getParticipants().add(user2);
        conversation.setCreatedAt(Instant.now());
        return conversation;
    }
    //mainly used to check if message bing sent by person A into covnersation object, is person A part of it.
    public boolean hasParticipant(UUID userId) {
        return participants.stream().anyMatch(u -> u.getId().equals(userId));
    }
}



