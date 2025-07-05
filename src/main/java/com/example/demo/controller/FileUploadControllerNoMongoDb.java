package com.example.demo.controller;

import com.example.demo.enteties.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.model.Conversation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.services.ConversationServiceNoMongoDb;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * REST controller for handling file uploads without MongoDB storage.
 * This controller accepts conversation files in JSON format, processes them,
 * and stores them in a HashMap for further use.
 */
@RestController
@RequestMapping("/api/noStorage")
public class FileUploadControllerNoMongoDb {

    // Service for managing conversations
    private final ConversationServiceNoMongoDb conversationService;

    // HashMap to store conversations, where the key is the folder name and the value is a list of conversations
    private final Map<String, ArrayList<Conversation>> conversationMap = new HashMap<>();
    /**
     * end-point for testing
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Server is up and running!");
    }

    @PostMapping("/pingpost")
    public ResponseEntity<String> pingpost(@RequestBody String name) {
        return ResponseEntity.ok("this is the text you sent - from server: "+name);
    }
    @GetMapping("/{folderName}")
    public ResponseEntity<ArrayList<Conversation>> getConversationsTest(@PathVariable String folderName){
        return ResponseEntity.ok(conversationService.getConversation(folderName));
    }

    /**
     * Constructor to inject the ConversationServiceNoMongoDb dependency.
     *
     * @param conversationService The service responsible for handling conversation-related logic.
     */
    @Autowired
    public FileUploadControllerNoMongoDb(ConversationServiceNoMongoDb conversationService) {
        this.conversationService = conversationService;
    }
    /**
     *  HttpSession dependency for Session based data authority
     *
     * @param HttpSession
     */
    @Autowired
    private HttpSession httpSession;


    /**
     * Endpoint for uploading a conversation file.
     *
     * This method accepts a JSON file representing a conversation and processes it.
     * The conversation is associated with a folder name and passed to the service for further processing.
     *
     * @param file The uploaded file containing the conversation in JSON format.
     * @param folderName The name of the folder associated with this conversation.
     * @return ResponseEntity containing either a success message or an error message.
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folderName") String folderName) {
        System.out.println("The method upload has been called");

        try {
            // Debugging: Print the JSON content of the uploaded file (before deserialization)
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            System.out.println("Received JSON content: " + fileContent);

            // Create an ObjectMapper for parsing JSON into a Conversation object
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the file input stream into a Conversation object
            Conversation conversation = objectMapper.readValue(file.getInputStream(), Conversation.class);

            // Debugging: Print the Conversation object (after deserialization)
            System.out.println("Deserialized Conversation being called from mongodb upload: " + conversation.toString());


            // Optionally use the folderName for further processing or logging
            conversation.setFolderName(folderName);

            //Add conversations to user session isntead of service
            // Retrieve the current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Get the current user's username

            // Check if UserSession already exists in the HttpSession
            UserSession userSession = (UserSession) httpSession.getAttribute(username);

            if (userSession == null) {
                // Create a new session if one doesn't exist
                userSession = new UserSession(username);
                httpSession.setAttribute(username, userSession); // Store the UserSession in the HttpSession
            }

            // Delegate the conversation processing to the service layer
            conversationService.addConversation(folderName, conversation);

            // Return a success response with a message
            return ResponseEntity.ok(Collections.singletonMap("message", "File processed successfully with folder: " + folderName));
        } catch (IOException e) {
            // Handle any I/O exception that occurs during file processing
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "File processing failed"));
        }
    }

}