/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

/**
 *
 * @author divme
 */
@Data
public class Participant {
    //getters and setters
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    Double avgWordCount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    Double avgWordsInAMessage;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    int totalWords;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    int TotalmessageCount;

    public void addWord(String word){
        int wordLength = word.length();
    }
}
