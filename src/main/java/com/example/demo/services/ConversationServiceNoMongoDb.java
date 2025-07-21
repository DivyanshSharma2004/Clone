package com.example.demo.services;

import com.example.demo.enteties.UserSession;
import com.example.demo.model.Conversation;
import com.example.demo.model.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * This service handles the logic related to conversations, attributes, and participants.
 * It provides methods to process conversations, add attributes to messages, and perform various analysis operations.
 */
@Service
public class ConversationServiceNoMongoDb {

    /**
     *  HttpSession dependency for Session based data authority
     *
     * @param HttpSession
     */
    @Autowired
    private HttpSession httpSession;

    // Map storing messages by their attributes for each participant.
    private ConcurrentHashMap<String, ConcurrentHashMap<String, ArrayList<Message>>> messagesByAttributesMap = new ConcurrentHashMap<>();


    // Central storage for conversations, keyed by folder name, with each key containing a list of conversations.
    private ConcurrentHashMap<String, ArrayList<Conversation>> conversationMap = new ConcurrentHashMap<>();

    //create method that when user clicks on the button it sends back a thyme leaf full of the data fo that particular stat
    //so it should take in file arraylist before then it should check the boolea nvalue if its upolaoded yet?
    //configure that in the js
    //and if its not uploaded then you just call the data on the arraylist.
    //in the controller take in
    //
    /**
     * Adds a conversation to the map of conversations, categorized by folder name.
     *
     * @param folderName    The folder name under which the conversation is stored.
     * @param conversation  The conversation object to add.
     */
    public void addConversation(String folderName, Conversation conversation) {
        // Retrieve the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the current user's username

        // Check if UserSession already exists in the HttpSession
        UserSession userSession = (UserSession) httpSession.getAttribute(username);
        userSession.addConversation(folderName,conversation); // Assuming UserSession has a method to add conversations
    }

    /**
     * Retrieves a list of conversations for a given participant or folder name.
     *
     * @param folderName The folder name.
     * @return An ArrayList of Conversation objects associated with the given name.
     */
    public ArrayList<Conversation> getConversation(String folderName) {
        // Retrieve the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Check if UserSession exists
        UserSession userSession = (UserSession) httpSession.getAttribute(username);
        if (userSession != null) {
            return userSession.getConversationsByFolder(folderName);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves a list of conversations for a given participant or folder name.
     *
     * @param folderName The folder name.
     * @param index The Message index.
     * @param timeStamp The messages time stamp.
     * @return An ArrayList of Conversation objects associated with the given name.
     */
    public ArrayList<Message> getMessagesByIndex(String folderName, int index, long timeStamp) {
        // Retrieve the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Check if UserSession exists
        UserSession userSession = (UserSession) httpSession.getAttribute(username);
        if (userSession != null) {
            return userSession.getMessagesByIndex(folderName, index, timeStamp);
        } else {
            return new ArrayList<Message>();
        }
    }

    /**
     * Retrieves a key:value of conversations for a given participant or folder name.
     *
     * @param name The folder name.
     * @return An map of folder name to arraylist of Conversation objects associated with the given name.
     */
    public ArrayList<Conversation> getConversationMap(String name) {
        // Assuming conversationMap is a class-level variable
        return conversationMap.getOrDefault(name, new ArrayList<>());
    }

    /**
     * Retrieves all the Folder names stored in the conversation map.
     *
     * @return A list of all Folder names.
     */
    public ArrayList<String> getAllConversationFolderNames() {
        return new ArrayList<>(conversationMap.keySet());
    }

}

