package com.example.demo.enteties;

import com.example.demo.model.Conversation;
import com.example.demo.model.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//As of now 12/02/24 this is not tied to userData
@Setter
@Getter
public class UserSession {
    private String username;

    HashMap<String,ArrayList<Conversation>> folderMap;

    // Constructor, getters, and setters
    public UserSession(String username) {
        this.username = username;
        folderMap = new HashMap<>();
    }

    // Add conversation method
    public void addConversation(String folderName, Conversation conversation) {
        // Ensure the folder exists
        folderMap.putIfAbsent(folderName, new ArrayList<>());
        // Add the conversation to the correct folder
        folderMap.get(folderName).add(conversation);
    }

    // Get conversations by folder name
    public ArrayList<Conversation> getConversationsByFolder(String folderName) {
        return folderMap.getOrDefault(folderName, new ArrayList<>());
    }

    //used for when user wants the surrounding messages. in later iterations make the count unlimited and so that it goes over ito next conversation list.
    public ArrayList<Message> getMessagesByIndex(String folderName, int index, long timeStamp){
        ArrayList<Message> messageArrayList = new ArrayList<>();
        if(folderMap.containsKey(folderName)){
            ArrayList<Conversation> convList = folderMap.get(folderName);
            for(Conversation conversation: convList){
                Message[] messages = conversation.getMessages();
                if(messages[index].getTimestamp_ms()==timeStamp){
                    for(int i = index-25; i < messages.length;i++){
                        Message message = messages[i];
                        messageArrayList.add(message);
                    }
                    break;
                }
            }
        }
        return messageArrayList;
    }
}
