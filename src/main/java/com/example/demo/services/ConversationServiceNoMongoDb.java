package com.example.demo.services;

import com.example.demo.enteties.Attributes;
import com.example.demo.enteties.ParticipantStats;
import com.example.demo.enteties.UserSession;
import com.example.demo.model.Conversation;
import com.example.demo.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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










//    /**
//     * Utility method to merge two integer maps, adding up values for matching keys.
//     *
//     * @param map1 The first map.
//     * @param map2 The second map.
//     * @return A merged map with summed values for shared keys.
//     */
//    private Map<String, Integer> mergeMaps(Map<String, Integer> map1, Map<String, Integer> map2) {
//        Map<String, Integer> mergedMap = new HashMap<>(map1);
//
//        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
//            String key = entry.getKey();
//            Integer value = entry.getValue();
//
//            mergedMap.merge(key, value, Integer::sum);
//        }
//
//        return mergedMap;
//    }




}
//TO DO: place for saved design choice will read back later:
///**
// * Placeholder method for processing a file and returning insights.
// * This method can be extended to calculate and return various metrics for the conversations in the file.
// *
// * @param file The file to process.
// * @return A map of metrics.
// * @throws IOException If there's an error reading the file.
// */
//public Map<String, Object> processFile(MultipartFile file) throws IOException {
//    // Placeholder implementation - to be filled with real metrics
//    Map<String, Object> result = new HashMap<>();
//    return result;
//}

//create a method that by participant name it calculates nah lets use objects instead.
   /* public ArrayList<String> participantsConversationsLaughCount(ArrayList<Conversation> conversations){
        ArrayList<String> participantLaughArraylist= new ArrayList<>;
        for(Conversation conversation:conversations){
            Map<String, Integer> temporaryMap = conversation.participantsLaughCount();
            // Merge the temporary map into the final map
            finalMap = mergeMaps(finalMap, temporaryMap);
        }
        return finalMap;
    }*/

//    // You can also add methods for specific queries, e.g., get conversations by participants
//    public List<Conversation> getConversationsByParticipants(String participants) {
//        return conversationMap.getOrDefault(participants, new ArrayList<>());
//    }
//TO DO: create a not version of the getMessagesWithAttributes like get messages that have color not vehicle yk.
//    public ArrayList<Conversation> getConversationAttributes(){
//
//    }
//check if attributes == null
//should return the conversation object?
//or do i just need the what? i should return the arraylist of convrsation object yeah


//you dont need to create a hashmap for this as you can simply add it to the you know what
//what does it return? should return arraylist of conversation list ? no thats what it should process
//if the person wants a sort of attribute look up yk they should accept what attributes they want to look up
//so in the lookup method i can simply call the process/ add yk the attributes into the messages
//? so method which one we working on
//method to uplaod and have all conversation arraylist attributes added in messages
//method to retrive all messages with x attributes  sorry arraylist of attributes

//okay now to make the method that classifies all your um conversation messages by attributes
//so 1 method to clasify
//1 to retrieve statistics
//1 to umm the question is how do i stop reloads say you exit the page how do i reload your um message statstics?
//i cando this by adding the instance of the pbject statistics for the person??
///hmm im a  bit confused
//take a look at the structure you made before

//you could createa a hashmap string: participantAttributes arraylist.
//method accepts what? nah you can crete the hashmap anywyhere
//you should add the attributes to the messages entity

//you can have a sort of set participants for hashmap methods in the upload.







//so youll have 2 different maps for each person. 1 full of real words and 1 full of not real words.
//now you can have another hashmap lol. so it becomes name/person: their alphabet hashmap.
//no instead you can have it so that you simply retrive the wors youeve ever said in all conversations, or in 1 singular conversation at a time

//method should take in an arraylist of conversation.
//create 2 hashmap, 1 waste, 1 real words, string name: Alphabet objects
//i could tweak the alphabet class to contain a default hashmap as well