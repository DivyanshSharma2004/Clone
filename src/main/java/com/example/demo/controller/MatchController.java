package com.example.demo.controller;

import com.example.demo.enteties.MatchDTO;
import com.example.demo.enteties.UserProfileDTO;
import com.example.demo.services.MatchService;
import com.example.demo.services.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private UserService userService; // to get current user

    @GetMapping("/home")
    public String showMatchPage() {
        return "/private/match"; // Thymeleaf return
    }

    //design left out due to switch over to uuid and exposing id to the front side
    //when user uses this method it fetches a random user from the database, gets some of their, stores the seconduserid intto the user session
    //shows the users profile like picture intreasts bio in a card.

    //end point for when the front end wants to find new potential match
    @GetMapping("/next")
    public ResponseEntity<List<UserProfileDTO>> getNextCandidateBatch() {
        List<UserProfileDTO> batch = matchService.getMatchCandidatesBatch();
        if (batch.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 if no candidates
        }
        return ResponseEntity.ok(batch);
    }
    //end point for when user rejects the person, gets stored in match db to not recommend same person again
    @PostMapping("/swipe-left")
    public ResponseEntity<?> swipeLeft(@RequestParam UUID targetProfileId) {
        try {
            matchService.swipeLeft(targetProfileId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //endpoint for when user swipes right on a user gets stored in the match service as pending

    @PostMapping("/swipe-right")
    public ResponseEntity<?> swipeRight(@RequestParam UUID targetProfileId) {
        try {
            matchService.swipeRight(targetProfileId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //end point for when user blocks another user

    @PostMapping("/block")
    public ResponseEntity<?> blockUser(@RequestParam UUID targetProfileId) {
        try {
            matchService.blockUser(targetProfileId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //endpoints for accepting requests etc

    @GetMapping("/accept-requests")
    public String showAcceptRequestsPage() {
        return "/private/accept-requests";
    }

    @GetMapping("/requests")
    public ResponseEntity<List<MatchDTO>> getPendingRequests() {
        List<MatchDTO> pendingRequests = matchService.getPendingRequestsForCurrentUser();
        return ResponseEntity.ok(pendingRequests);
    }

    @PostMapping("/{matchId}/accept")
    public ResponseEntity<?> acceptMatch(@PathVariable UUID matchId) {
        try {
            matchService.acceptMatch(matchId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{matchId}/reject")
    public ResponseEntity<?> rejectMatch(@PathVariable UUID matchId) {
        try {
            matchService.rejectMatch(matchId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //and now finally a query that fetches all friendships that are active or matches that are active for me
    //should create a reusabel method that takes in different parameteres to if user A wanted to change relationship status, it effect user b friendship too


}