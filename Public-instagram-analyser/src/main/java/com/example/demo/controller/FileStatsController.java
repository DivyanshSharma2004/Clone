package com.example.demo.controller;

import com.example.demo.enteties.ParticipantStats;
import com.example.demo.enteties.RequestData;
import com.example.demo.model.Conversation;
import com.example.demo.model.Message;
import com.example.demo.services.AttributesService;
import com.example.demo.services.ConversationServiceNoMongoDb;
import com.example.demo.services.ConversationStatsService;
import com.example.demo.services.ParticipantStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for retrieving statistics and conversations related to participants.
 * This controller provides endpoints to retrieve participant names, conversation counts,
 * and specific conversation details such as laugh counts.
 */
@Controller
@RequestMapping(value = "/api/conversation", produces = "application/json; charset=UTF-8")
public class FileStatsController {

    @Autowired
    private ConversationServiceNoMongoDb conversationService;
    @Autowired
    private AttributesService attributesService;
    @Autowired
    private ParticipantStatsService participantStatsService;
    @Autowired
    ConversationStatsService conversationStatsService;

    /**
     * Creates thymeleaf dynamic page
     *
     * @return thymeleaf page
     */
    @GetMapping("/fileStats/{folderName}")
    public String dashBoard(Model model, @PathVariable String folderName) {
        // Get conversations that user requested
        ArrayList<Conversation> conversationList = conversationService.getConversation(folderName);

        // Get basic stats from these files
        List<Map.Entry<String, Double>> statsMap = conversationStatsService.getConversationStats(conversationList);

        model.addAttribute("folderName", folderName);
        model.addAttribute("fileStats", statsMap);  // Pass the actual stats list
        return "/private/fileStats";  // Thymeleaf template name
    }

    @GetMapping("/fileStatsTest")
    public String fileStatsTest() {
        // Logic to handle the Thymeleaf view and pass data to the view
        System.out.println("the method fileStatsTest has been called");
        return "UserStatsUpload";  // Thymeleaf template name
    }

    //if you want the messages to be generated deeply inbedded using ajax etc and react use this or else send thymeleaf response back.
    @GetMapping("/getSurroundingMessages")
    public ArrayList<Message> getSurroundingMessages(@RequestParam String folderName, @RequestParam int index, @RequestParam long timeStamp ) {
       ArrayList<Message> messageArrayList;
        messageArrayList = conversationService.getMessagesByIndex(folderName,index,timeStamp);
       return messageArrayList;
    }

    /**
     * Retrieves the Message Objects by Certain attributes
     *
     * @RequestBody RequestData an object that contains folderName, desiredAttributesSet, undesiredAttributesSet
     * or a 200 response with an empty map if messages by attributes not found.
     */
    @PostMapping("/getMessagesByAttributes")
    public ResponseEntity<ArrayList<Message>> getMessagesByAttributes(
            @RequestBody RequestData requestData) { // Accept the data as JSON in the request body

        // Extract data from the request body
        String folderName = requestData.getFolderName();
        System.out.println("desired attributes: "+ requestData.getDesiredAttributes());
        System.out.println("unDesired attributes: "+ requestData.getUndesiredAttributes());
        HashSet<String> desiredAttributesSet = requestData.getDesiredAttributes() != null
                ? new HashSet<>(requestData.getDesiredAttributes()) : new HashSet<>();
        HashSet<String> undesiredAttributesSet = requestData.getUndesiredAttributes() != null
                ? new HashSet<>(requestData.getUndesiredAttributes()) : new HashSet<>();

        // Assuming you have a service that processes the conversation and messages
        ArrayList<Conversation> conversationList = conversationService.getConversation(folderName);
        return new ResponseEntity<>(conversationStatsService.getMessagesByAttributes(conversationList, desiredAttributesSet, undesiredAttributesSet), HttpStatus.OK);
    }


    /**
     * Retrieves the laugh count for all conversations of a specific participant.
     *
     * @param name The name of the participant.
     * @return ResponseEntity containing a map of participant stats with laugh counts,
     * or a 200 response with an empty map if no laughs are found.
     */
    @GetMapping("/conversation/{name}/getLaughs")
    public ResponseEntity<Map<String, ParticipantStats>> getLaughCount(@PathVariable String name) {
        return new ResponseEntity<>(participantStatsService.participantsConversationsLaughCount(conversationService.getConversation(name)), HttpStatus.OK);
    }
    //should I give user the ability to download json???
}


