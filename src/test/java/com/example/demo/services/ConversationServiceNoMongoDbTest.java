package com.example.demo.services;


import com.example.demo.model.Conversation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConversationServiceNoMongoDbTest {

    private ConversationServiceNoMongoDb conversationService;

    @BeforeEach
    void setUp() {
        conversationService = new ConversationServiceNoMongoDb();
    }

    /**
     * Test to verify that a conversation is added to the map of conversations, categorized by folder name.
     */
    @Test
    public void addConversationTest() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Arrange: Set up ObjectMapper and the conversation object
        ObjectMapper mapper = new ObjectMapper();
        Conversation conversation = mapper.readValue(new File("src/test/resources/Conversation.json"), Conversation.class);

        // Act: Add the conversation to the service
        conversationService.addConversation("folder1", conversation);

        // Access the private field "conversationMap" directly
        Field mapField = ConversationServiceNoMongoDb.class.getDeclaredField("conversationMap");
        mapField.setAccessible(true);
        Map<String, List<Conversation>> internalMap = (Map<String, List<Conversation>>) mapField.get(conversationService);

        // Assert: Verify that "folder1" contains the added conversation
        assertTrue(internalMap.containsKey("folder1"));
        assertTrue(internalMap.get("folder1").contains(conversation));
    }

    /**
     * Test to verify that retrieving conversations for a given folder name returns the correct list.
     */
    @Test
    public void getConversationTest() throws IOException {
        // Arrange
        ObjectMapper mapper = new ObjectMapper();
        Conversation conversation = mapper.readValue(new File("src/test/resources/Conversation.json"), Conversation.class);
        conversationService.addConversation("folder2", conversation);

        // Act
        List<Conversation> result = conversationService.getConversation("folder2");

        // Assert
        assertEquals(1, result.size());
        assertEquals(conversation, result.get(0));

        // Additional case: retrieve from a non-existent folder
        List<Conversation> emptyResult = conversationService.getConversation("nonExistentFolder");
        assertTrue(emptyResult.isEmpty());
    }

    /**
     * Test to verify that all folder names in the conversation map are retrieved correctly.
     */
    @Test
    public void getAllConversationFolderNamesTest() throws IOException {
        // Arrange
        ObjectMapper mapper = new ObjectMapper();
        Conversation conversation1 = mapper.readValue(new File("src/test/resources/Conversation.json"), Conversation.class);
        Conversation conversation2 = new Conversation();  // Create a second conversation with unique data

        conversationService.addConversation("folderA", conversation1);
        conversationService.addConversation("folderB", conversation2);

        // Act
        List<String> folderNames = conversationService.getAllConversationFolderNames();

        // Assert
        assertEquals(2, folderNames.size());
        assertTrue(folderNames.contains("folderA"));
        assertTrue(folderNames.contains("folderB"));
    }

}

