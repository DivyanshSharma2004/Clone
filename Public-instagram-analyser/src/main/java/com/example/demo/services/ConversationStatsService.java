package com.example.demo.services;

import com.example.demo.model.Conversation;
import com.example.demo.model.ConversationStats;
import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConversationStatsService {
    @Autowired
    ConversationServiceNoMongoDb conversationService;
    @Autowired
    AttributesService attributesService;

    /**
     * Computes the average percentage stats for each attribute across all provided conversations.
     *
     * This method processes the attribute percentage stats of each conversation and accumulates them
     * to calculate the average for each attribute. The average percentage for each attribute is
     * calculated by dividing the sum of percentages across all conversations by the total number of conversations.
     *
     * @param conversations The list of conversations to analyze.
     * @return A HashMap containing the average percentage of each attribute across all conversations.
     *         The keys are attribute names (String), and the values are the average percentages (Double).
     */
    public List<Map.Entry<String, Double>> getConversationStats(ArrayList<Conversation> conversations) {
        System.out.println("the method getConversationStats itself has been called");
        // Ensure attributes are processed
        processAttributes(conversations);
        // Create a map to accumulate the percentage stats
        HashMap<String, Double> cumulativeStats = new HashMap<>();
        // Sum up the attribute percentages across all conversations
        int totalConversations = conversations.size();

        for (Conversation c : conversations) {
            // Get the stats for each conversation
            HashMap<String, Double> conversationStats = c.getAttributePercentageStats();

            // Accumulate the percentage stats for each attribute
            for (Map.Entry<String, Double> entry : conversationStats.entrySet()) {
                String attribute = entry.getKey();
                double percentage = entry.getValue();

                // If the attribute already exists in the cumulative map, add the value
                cumulativeStats.put(attribute, cumulativeStats.getOrDefault(attribute, 0.0) + percentage);
            }
        }
        // Average the percentages for each attribute (if totalConversations > 0)
        for (Map.Entry<String, Double> entry : cumulativeStats.entrySet()) {
            String attribute = entry.getKey();
            double totalPercentage = entry.getValue();
            double averagePercentage = totalPercentage / totalConversations;
            cumulativeStats.put(attribute, averagePercentage);
        }
        // Convert the cumulative stats map to a list of entries
        List<Map.Entry<String, Double>> sortedStats = new ArrayList<>(cumulativeStats.entrySet());

        // Sort the list by value in descending order
        sortedStats.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        return sortedStats; // Return sorted list
    }

    /**
     * returns the list of messages that contain the desired attributes.
     * If the attributes of a conversation have not been processed yet, they are processed.
     *
     * @param conversations The list of conversations to process.
     * @param desiredAttributes The list of attributes to search messages for.
     * @return totalMessagesList contains all messages that contain all the attributes requested.
     */
    public ArrayList<Message> getMessagesByAttributes(ArrayList<Conversation> conversations, HashSet<String> desiredAttributes, HashSet<String> unDesiredAttributes) {
        //just checks if conversation object has attributes processed
        ArrayList<Message> totalMessagesList = new ArrayList<>();
        processAttributes(conversations);
        for(Conversation conversation: conversations){
            //adds all message objects from the conversation to the total messages list
            ArrayList<Message> messagesList = conversation.getMessagesByAttributes(desiredAttributes, unDesiredAttributes);
            totalMessagesList.addAll(messagesList);
        }
        return totalMessagesList;
    }
    /**
     * Processes the attributes for each conversation in the provided list.
     * If the attributes of a conversation have not been processed yet, they are processed.
     *
     * @param conversations The list of conversations to process.
     */
    @Async
    public void processAttributes(ArrayList<Conversation> conversations) {
        System.out.println("the method processAttributes has itself been called");
        // Iterate through each conversation in the list
        System.out.println("the size of the conversations list is "+conversations.size());
        for (Conversation c : conversations) {
            System.out.println("the conversation attributes processed boolean are: "+!c.getBoolStatsProcessed());
            // Check if the attributes for this conversation have already been processed
            if (!c.getBoolStatsProcessed()) {
                System.out.println("the commands inside getBoolStatsProcessed if loop has been called ");
                //initialise the attributes map in conversation objects
                c.initializeAttributesMap();
                // If attributes have not been processed, process them using the attribute service

                attributesService.addAttributesToMessages(c);

                // Mark this conversation as processed to prevent reprocessing
                c.setBoolAttributesProcessed(true);
            }
        }
    }

}
