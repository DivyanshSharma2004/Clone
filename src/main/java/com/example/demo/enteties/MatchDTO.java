package com.example.demo.enteties;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MatchDTO {
    private UUID matchId;
    private UUID senderId;
    private String senderName;
    private String senderAvatarUrl;
    private String bio;
}
