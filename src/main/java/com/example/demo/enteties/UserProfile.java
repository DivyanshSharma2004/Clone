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
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    private String username;
    private String profilePic;

    private String bio;

    @ElementCollection
    private List<String> interests;

}
