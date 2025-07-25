package com.example.demo.enteties;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import java.util.List;

//will be sent to the front end everytime a new candidate is called
@Setter
@Getter
public class UserProfileDTO {
    private UUID id;
    private String name;
    private String profilePic;
    private String bio;
    private List<String> interests;

    // Constructor
    public UserProfileDTO(UUID id,String name, String profilePic, String bio, List<String> interests) {
       this.id = id;
        this.name = name;
        this.profilePic = profilePic;
        this.bio = bio;
        this.interests = interests;
    }
}
