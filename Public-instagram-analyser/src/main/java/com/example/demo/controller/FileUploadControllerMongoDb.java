package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.model.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.services.ConversationService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * REST controller for handling file uploads and retrievals in MongoDB.
 * This controller allows for uploading conversation files in JSON format,
 * processing them, saving them to the database, and retrieving them based on ID.
 */
@RestController
@RequestMapping("/api/json")
public class FileUploadControllerMongoDb{

    @Autowired
    private ConversationService applicationService;

    /**
     * Endpoint for handling the upload of a conversation file.
     *
     * This method processes the uploaded file, converts it to a Conversation object,
     * and saves it to the database.
     *
     * @param file The uploaded file containing the conversation in JSON format.
     * @return ResponseEntity containing a success message if the file was processed and saved successfully,
     *         or an error message if the file processing fails.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Process the file and extract conversation data
            Conversation conversation = processFile(file);
            applicationService.saveApplication(conversation);

            System.out.println("save happened");
            return ResponseEntity.ok("File processed and conversation saved.");
        } catch (Exception e) {
            // Handle any exception that occurs during file processing
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing failed.");
        }
    }

    /**
     * Endpoint for retrieving a saved conversation by its ID.
     *
     * This method retrieves a Conversation object from the database based on the provided ID.
     *
     * @param id The ID of the conversation to retrieve.
     * @return ResponseEntity containing the retrieved conversation object,
     *         or a 404 status if the conversation is not found.
     */
    @GetMapping("/retrieve")
    public ResponseEntity<Conversation> getJsonFile(@RequestParam("id") String id) {
        System.out.println("retrieve was called");

        // Retrieve the conversation from the service by its ID
        Conversation conversation = applicationService.getApplicationById(id);
        if (conversation != null) {
            System.out.println("return happened");
            return ResponseEntity.ok(conversation);
        } else {
            System.out.println("return didn't happen");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Helper method for processing the uploaded file.
     *
     * This method reads the uploaded file's content, assuming it's in JSON format,
     * and deserializes it into a Conversation object.
     *
     * @param file The uploaded MultipartFile containing the conversation in JSON format.
     * @return The deserialized Conversation object.
     */
    private Conversation processFile(MultipartFile file) {
        Conversation conversation = new Conversation();

        try {
            // Convert the file's content to a string using UTF-8 encoding
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);

            // Deserialize the JSON content into a Conversation object using ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            conversation = objectMapper.readValue(content, Conversation.class);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately (logging, etc.)
        }

        return conversation;
    }

}

