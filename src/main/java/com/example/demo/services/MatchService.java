package com.example.demo.services;

import com.example.demo.enteties.Match;
import com.example.demo.enteties.User;
import com.example.demo.enteties.UserProfile;
import com.example.demo.enteties.UserProfileDTO;
import com.example.demo.repository.MatchRepository;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserRepository userRepository;
    //User session object to bind data too and recall data from.
    @Autowired
    private HttpSession httpSession;
    //batch size for the getMatchCandidatesBatch to use
    private static final int BATCH_SIZE = 10;

    // Re-usable method to get current user ID from session
    private long getCurrentUserId() {
        long userId = (long) httpSession.getAttribute("userId");
        if (userId == 0.0) {
            throw new RuntimeException("User is not logged in or session expired");
        }
        return userId;
    }

    // Re-usable method to get potentialMatchId from session
    private long getPotentialMatchIdFromSession() {
        Object matchIdObj = httpSession.getAttribute("potentialMatchId");

        if (matchIdObj == null) {
            throw new IllegalStateException("No potential match found in session.");
        }

        return (long) matchIdObj;
    }

    //method returns an optional userProfileDTO, confirmed in the controller,
    //method gets random user makes sure the user isnt in the requesting users match list already
    //TODO: add security if user if blocked by matchCandidate what to do
    //TODO: add a check to make sure the users own id isnt sent back to them


    //TODO: add extra security and fail cases
    public List<UserProfileDTO> getMatchCandidatesBatch() {
        long currentUserId = getCurrentUserId();
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile currentUserProfile = userProfileRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        List<UserProfile> batch = userProfileRepository.findUnmatchedRandomBatch(currentUserProfile.getId(), BATCH_SIZE);

        List<UserProfileDTO> result = new ArrayList<>();
        for (UserProfile profile : batch) {
            boolean matched = matchRepository.existsByUser1AndUser2(currentUserProfile, profile)
                    || matchRepository.existsByUser1AndUser2(profile, currentUserProfile);
            if (!matched) {
                UserProfileDTO dto = new UserProfileDTO(
                        profile.getId(),
                        profile.getName(),
                        profile.getProfilePic(),
                        profile.getBio(),
                        profile.getInterests()
                );
                result.add(dto);
            }
        }

        return result;
    }

    public void swipeLeft(UUID targetProfileId) {
        createMatch(getCurrentUserId(), targetProfileId, Match.MatchStatus.ENDED);
    }

    //TODO: add extra converage to check if when User A swiped on user B did user b also have them as pending, if so change both to ACTIVE
    public void swipeRight(UUID targetProfileId) {
        createMatch(getCurrentUserId(), targetProfileId, Match.MatchStatus.PENDING);
    }

    //add extra coverage to not reccomend user B to user A if user B blocked user A
    public void blockUser(UUID targetProfileId) {
        createMatch(getCurrentUserId(), targetProfileId, Match.MatchStatus.BLOCKED);
    }


    private void createMatch(long currentUserId, UUID targetProfileId, Match.MatchStatus status) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile user1 = userProfileRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("UserProfile not found for current user"));

        UserProfile user2 = userProfileRepository.findById(targetProfileId)
                .orElseThrow(() -> new RuntimeException("UserProfile not found for match id"));

        //save the match to the database
        Match match = new Match();
        match.setUser1(user1);
        match.setUser2(user2);
        match.setStatus(status);
        match.setMatchedAt(Instant.now());
        matchRepository.save(match);
    }
}


