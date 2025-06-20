/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author divme
 */
@Data
public class Share {
    private String link;

    @JsonProperty("share_text")
    private String shareText;

    @JsonProperty("original_content_owner")
    private String originalContentOwner;

    @JsonProperty("profile_share_username")
    private String profileShareUsername;

    @JsonProperty("profile_share_name")
    private String profileShareName;
 
}