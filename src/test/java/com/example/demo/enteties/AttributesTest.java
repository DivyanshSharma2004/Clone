package com.example.demo.enteties;

import com.example.demo.model.Attributes;
import com.example.demo.model.Conversation;
import com.example.demo.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributesTest {
    static Attributes attributes;
    @BeforeAll
    static void setup() {
        attributes = new Attributes();
    }
    @Test
    void tokenizeAndRemoveFillers() {
        // Test sentence
        String sentence = "The quick brown fox jumps over the lazy dog. \uD83D\uDE00 \uD83D\uDE00";
        // Expected output after removing fillers
        List<String> expected = List.of("quick", "brown", "fox", "jumps","over", "lazy","dog" ,"\uD83D\uDE00", "\uD83D\uDE00");

        // Call the method to get the result
        List<String> result = Attributes.tokenizeAndRemoveFillers(sentence);

        // Assert the result matches the expected output
        assertEquals(expected, result);
    }
    @Test
    void checkForJsonEmojis(){
        // Test sentence
        String sentence = "There are two types of emojis here \\uD83D\\uDE00 \\uD83D\\uDE00";

        attributes.init();
        boolean answer = attributes.containsEmojisInJsonString(sentence);
        assertEquals(true,answer);
    }
    @Test
    void checkForGraphicEmoji(){
        String sentence = "There are two types of emojis here \uD83D\uDE00 \uD83D\uDE00\";";

        attributes.init();//meant to fail as were calling a method that checks for json string emoji not graphic
        boolean answer = attributes.containsEmojis(sentence);
        assertEquals(true,answer);
    }
    @Test
    void checkForNoEmoji(){
        // Test sentence
        String sentence = "There are two types of emojis here";

        attributes.init();
        boolean answer = attributes.containsEmojisInJsonString(sentence);
        assertEquals(false,answer);
    }

    @Test
    void checkJsonFileForEmojis() throws IOException {
        //Read Conversation object from file
        ObjectMapper mapper = new ObjectMapper();
        Conversation conversation = mapper.readValue(new File("src/test/resources/EmojiConversation.json"), Conversation.class);
        conversation.initializeAttributesMap();

        Message[] messages = conversation.getMessages();
        for(Message message: messages){
            attributes.addAttribute(message, conversation);
        }
        //Prepare params and Retrieve emoji attribute from Conversation Object
        HashSet<String> desiredSet = new HashSet<>();
        desiredSet.add("emojis");
        HashSet<String> undesiredSet = new HashSet<>();
        ArrayList<Message> messageList = conversation.getMessagesByAttributes(desiredSet, undesiredSet);
        System.out.println(messageList);
        System.out.println("size: "+messageList.size());

        assertEquals(true,messageList!=null);
    }



//    @Test
//    void addAttribute() throws IOException {
//        // Arrange
//        ObjectMapper mapper = new ObjectMapper();
//        //create conversation object for testing
//        Conversation conversation = mapper.readValue(new File("src/test/resources/Conversation.json"), Conversation.class);
//        //create message obj for testing
//        String json = "{\"sender_name\": \"insta_hates_me_lol\", \"timestamp_ms\": 1713900135040, \"content\": \"place time HOW\", \"is_geoblocked_for_viewer\": false}";
//
//        Message message = null;
//        try {
//            message = mapper.readValue(json, Message.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("message content "+message.getContent());
//        //test the method
//        attributes.addAttribute(message,conversation);
//        Set<String> expected = new HashSet<>();
//        Collections.addAll(expected, "place", "time");
//        assertEquals(expected, message.getAttributeList());
//    }

}
