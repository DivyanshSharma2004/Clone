package com.example.demo.enteties;

import jakarta.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "user_vector")
public class UserVector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserProfile user;

    // Store the embedding vector - this requires custom type mapping or native query
    @Column(columnDefinition = "vector(200)", nullable = false)
    private float[] embedding;

    // Constructors, getters, setters
    public UserVector() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UserProfile getUser() { return user; }
    public void setUser(UserProfile user) { this.user = user; }

    public float[] getEmbedding() { return embedding; }
    public void setEmbedding(float[] embedding) { this.embedding = embedding; }
}
