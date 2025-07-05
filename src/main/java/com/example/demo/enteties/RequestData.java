package com.example.demo.enteties;

import java.util.List;

// DTO (Data Transfer Object) to represent the request body
public class RequestData {
    private String folderName;
    private List<String> desiredAttributes;
    private List<String> undesiredAttributes;

    // Getters and Setters
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<String> getDesiredAttributes() {
        return desiredAttributes;
    }

    public void setDesiredAttributes(List<String> desiredAttributes) {
        this.desiredAttributes = desiredAttributes;
    }

    public List<String> getUndesiredAttributes() {
        return undesiredAttributes;
    }

    public void setUndesiredAttributes(List<String> undesiredAttributes) {
        this.undesiredAttributes = undesiredAttributes;
    }
}
