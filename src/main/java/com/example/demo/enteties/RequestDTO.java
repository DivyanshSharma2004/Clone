package com.example.demo.enteties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class RequestDTO {
    // Getters and Setters
    private String folderName;
    private List<String> desiredAttributes;
    private List<String> undesiredAttributes;

}
