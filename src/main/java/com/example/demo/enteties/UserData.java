package com.example.demo.enteties;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "user_data")
public class UserData {
    @Id
    private String id;
    private String userId; // To associate data with a specific user
    private String dataField; // Replace with actual data fields

    public void setUserId(String userId) {
    }
}

