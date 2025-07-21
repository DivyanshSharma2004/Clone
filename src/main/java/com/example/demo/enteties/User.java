package com.example.demo.enteties;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String googleId;
    private String email;
    private String name;
    private String pictureUrl;

    private String role; // e.g., ROLE_USER
}
