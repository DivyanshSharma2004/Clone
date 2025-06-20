package com.example.demo.repository;

import com.example.demo.enteties.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDataRepository extends MongoRepository<UserData, String> {
    List<UserData> findByUserId(String userId);
}

