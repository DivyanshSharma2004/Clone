package com.example.demo.enteties;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//uses long id for internal as doesnt need to be as secure and easier for testing
    private String email;
    private String name;
    private String googleId;
    private String pictureUrl;
    private String role; // e.g., ROLE_USER
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)//any operation in user will be cascaded to userProfile
    private UserProfile profile;
}
