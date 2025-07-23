package com.example.demo.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "user_profile")
public class UserProfile {
    // Getters & Setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;//uses uuid for userprofile as needs to be more secure

    private String name;
    private String profilePic;
    private String bio;

    @ElementCollection
    private List<String> interests;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
