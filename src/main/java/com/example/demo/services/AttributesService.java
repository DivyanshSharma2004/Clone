package com.example.demo.services;

import com.example.demo.model.Attributes;
import com.example.demo.model.Conversation;
import com.example.demo.model.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class AttributesService {
    // Holds the attributes used to categorize messages. Initialized when needed.
    @Autowired
    Attributes attributes;

    /**
     * Loads the attributes from a JSON file if they haven't been loaded already.
     */
    //ToDO: have this part in the construction of attributes and make it a bean
    @PostConstruct
    public void init() {
        loadAttributesIfNeeded();
    }

    // Load attributes from resources if not already loaded
    public void loadAttributesIfNeeded() {

    }
    /**
     * Adds attributes to each message in a list of conversations. If the attributes have already been initialized,
     * the method applies them to the messages. Attributes are loaded from a JSON file if not initialized.
     *
     * @param conversationList List of conversations to which attributes will be added.
     */
    public void addAttributesToMessages(ArrayList<Conversation> conversationList) {
        // Add attributes to each message in every conversation
        for (Conversation conversation : conversationList) {
            Message[] messages = conversation.getMessages();
            for (Message message : messages) {
                attributes.addAttribute(message,conversation);
            }
        }
    }
    /**
     * Adds attributes to each message in a  conversation. If the attributes have already been initialized,
     * the method applies them to the messages. Attributes are loaded from a JSON file if not initialized.
     *
     * @param conversation List of conversations to which attributes will be added.
     */
    @Async
    public void addAttributesToMessages(Conversation conversation) {
        System.out.println("the method addAttributesToMessages has itself been called");
        // Add attributes to each message in conversation
            Message[] messages = conversation.getMessages();
            String folderName = conversation.getFolderName();
            for(int i = 0; i<messages.length;i++){
                Message message = messages[i];
                message.setMessageIndex(i);
                message.setFoldername(folderName);
                attributes.addAttribute(message,conversation);
            }
    }

    //TODO: repurpose this for the 1000 messages attribute stats, remember to add edge case where less than 1000 messages


    //TODO: CHANGE THIS DESIGN PROCESS HAVE IT CHECK IF CERTAIN CONVERSATIONS HAVE THEIR ATTRIBUTES PROCESSED OR NOT OR ELSE IT ADDS ATTRIBUTES TO THE SAME MESSAGES EVERY TIME
    /*TODO: design process: this method will 1. be broken down to a. add attributes and b. retrieve messages by attributes. this method should only be retrieve methods by attributes.
     then during the search bar or when the thymeleaf page is being rendered it will then be processed. honestly considering using some other language to loop through these messages for speed.
     */

//TODO: change in treturn structure would be good too in the sense that the messages are divided by sender name. but that might be a problem for sorting etc.
//i wonder if theres a way to have the best of both worlds.

    /**
     * Retrieves messages from conversations that match the specified attributes.
     * The method first adds attributes to messages and then checks if the attributes are present in the message.
     *
     * @param conversationList List of conversations to process.
     * @param desiredAttributes    List of attributes to match.
     * @return A HashMap where the key is the sender's name and the value is the content of the message.
     */
    public HashMap<String, String> getMessagesWithAttributes(ArrayList<Conversation> conversationList, HashSet<String> desiredAttributes, HashSet<String> unDesiredAttributes) {

        HashMap<String, String> messagesWithAttributes = new HashMap<>();
        for (Conversation conversation : conversationList) {
            Message[] messageList = conversation.getMessages();
            for (Message message : messageList) {
                // Check if the message contains the desired attributes
                if (message.areAttributesPresent(desiredAttributes, unDesiredAttributes)) {
                    messagesWithAttributes.put(message.getSender_name(), message.getContent());
                }
            }
        }
        return messagesWithAttributes;
    }
}
