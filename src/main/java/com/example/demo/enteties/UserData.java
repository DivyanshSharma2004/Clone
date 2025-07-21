package com.example.demo.enteties;


import lombok.Data;

@Data
public class UserData {
    private String id;
    private String userId; // To associate data with a specific user
    private String dataField; // Replace with actual data fields

    public void setUserId(String userId) {
    }
}

