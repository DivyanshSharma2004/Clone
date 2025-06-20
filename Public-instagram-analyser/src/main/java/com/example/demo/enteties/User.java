package com.example.demo.enteties;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String role; // e.g., ROLE_USER, ROLE_ADMIN

    public void setUsername(String username) {

    }
}
