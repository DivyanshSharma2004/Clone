package com.example.demo.services;

import com.example.demo.enteties.*;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    //batch size for the getMatchCandidatesBatch to use
    private static final int BATCHSIZE = 10;

    // Re-usable method to get current user ID from session
    public long getCurrentUserId() {
        long userId = (long) httpSession.getAttribute("userId");
        if (userId == 0.0) {
            throw new RuntimeException("User is not logged in or session expired");
        }
        return userId;
    }
    //made with help of ai (i undersatnd all of the code)
    public UUID getCurrentUserProfileId() {
        // Try session cache
        UUID profileId = (UUID) httpSession.getAttribute("profileId");
        if (profileId != null) {
            return profileId;
        }

        // Fallback: get userId from session (assuming it's there)
        Long userId = (long) httpSession.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User is not logged in or session expired (userId missing)");
        }

        // Find profile for this user
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No profile found for userId: " + userId));

        // Cache in session for next time
        httpSession.setAttribute("profileId", profile.getId());

        return profile.getId();
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
        long currentUserId = getCurrentUserId();//user id
        User currentUser = userRepository.findById(currentUserId)//get user object via id
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile currentUserProfile = userProfileRepository.findByUser(currentUser)//get user profile via id
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        
        //get a batch of potential friend recommendations 
        List<UserProfile> batch = userProfileRepository.findUnmatchedRandomBatch(currentUserProfile.getId(), BATCHSIZE);
        
        //create dtos 
        List<UserProfileDTO> result = new ArrayList<>();
        for (UserProfile profile : batch) {
            // Skip if users own profile  
            if (profile.getId().equals(currentUserProfile.getId())) {
                continue;
            }
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

    //method that prompts the repository for wherever current user profile is in user2_id and status is pending retrives these mathches, creates a dto adds it to a list and return
    public List<MatchDTO> getPendingRequestsForCurrentUser() {
        UUID currentProfileId = getCurrentUserProfileId();//profile id
        
        //list containing all user friend requests 
        List<Match> pendingRequests = matchRepository.findPendingRequestsForUser(
                currentProfileId, Match.MatchStatus.PENDING
        );
        //dto to hold friend requests 
        List<MatchDTO> result = new ArrayList<>();
        
        //for each entry in pending requests create dto 
        for (Match match : pendingRequests) {
            UserProfile sender = match.getRequester();

            MatchDTO dto = new MatchDTO();
            dto.setMatchId(match.getId());
            dto.setSenderId(sender.getId());
            dto.setSenderName(sender.getName());
            dto.setSenderAvatarUrl(sender.getProfilePic());
            dto.setBio(sender.getBio());

            result.add(dto);
        }
        return result;
    }
    

    //method to reject match 
    public void swipeLeft(UUID targetProfileId) {
        createMatch(getCurrentUserId(), targetProfileId, Match.MatchStatus.ENDED);
    }

    
    public void swipeRight(UUID targetProfileId) {
        createMatch(getCurrentUserId(), targetProfileId, Match.MatchStatus.PENDING);
    }
    //method to block 
    //add extra coverage to not reccomend user B to user A if user B blocked user A
    public void blockUser(UUID targetProfileId) {
        createMatch(getCurrentUserId(), targetProfileId, Match.MatchStatus.BLOCKED);
    }

    //create match wether PENDING, ACTIVE, BLOCKED, ENDED
    private void createMatch(long currentUserId, UUID targetProfileId, Match.MatchStatus status) {
        User currentUser = userRepository.findById(currentUserId)//make sure user is in repo
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile user1Profile = userProfileRepository.findByUser(currentUser)//make sure user has a profile before they try match
                .orElseThrow(() -> new RuntimeException("UserProfile not found for current user"));

        UserProfile user2Profile = userProfileRepository.findById(targetProfileId)//make sure target id exists 
                .orElseThrow(() -> new RuntimeException("UserProfile not found for target id"));
        
        //store in db in a order smaller to large to avoid duplicate entries and quicker lookup 
        UserProfile firstUser, secondUser;
        if (user1Profile.getId().compareTo(user2Profile.getId()) < 0) {
            firstUser = user1Profile;
            secondUser = user2Profile;
        } else {
            firstUser = user2Profile;
            secondUser = user1Profile;
        }
        
        //get match if it alredy exits to change state 
        Optional<Match> existingMatch = matchRepository.findByUser1IdAndUser2Id(firstUser.getId(), secondUser.getId());

        if (existingMatch.isPresent()) {//if match alredy exists 
            Match match = existingMatch.get();
            match.setStatus(status);
            match.setMatchedAt(Instant.now());
            matchRepository.save(match);
        } else {
            Match match = new Match();
            match.setUser1(firstUser);
            match.setUser2(secondUser);
            match.setStatus(status);
            match.setMatchedAt(Instant.now());
            match.setRequester(user1Profile);  // current user is requester
            matchRepository.save(match);
        }
    }
    
    //accept match method 
    @Transactional
    public void acceptMatch(UUID matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("match not found"));

        UUID currentProfileId = getCurrentUserProfileId();

        if (match.getRequester().getId().equals(currentProfileId)) { //incase logic breaks
            throw new RuntimeException("user cant accept their own request");
        }

        match.setStatus(Match.MatchStatus.ACTIVE);
        matchRepository.save(match);

        UserProfile user1 = match.getUser1();
        UserProfile user2 = match.getUser2();

        // store friendship with uuid ordered smaller first
        UserProfile first = user1.getId().compareTo(user2.getId()) < 0 ? user1 : user2;
        UserProfile second = first == user1 ? user2 : user1; //abandonded appraoch speed > size of db.

        // create conversation if not exists, safer for when people stop being friends and then again
        Conversation conversation = conversationRepository
                .find1on1Conversation(user1.getId(), user2.getId())
                .orElseGet(() -> conversationRepository.save(Conversation.create1on1(user1, user2)));

        // create bidirectional friendships (for quick lookup)
        if (!friendshipRepository.existsBetween(user1, user2)) {
            Friendship friendship1 = new Friendship(user1, user2, conversation);
            friendship1.setCreatedAt(Instant.now());

            Friendship friendship2 = new Friendship(user2, user1, conversation);
            friendship2.setCreatedAt(Instant.now());

            friendshipRepository.save(friendship1);
            friendshipRepository.save(friendship2);
        }
    }

    @Transactional
    public void rejectMatch(UUID matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        UUID currentProfileId = getCurrentUserProfileId();
        if (match.getRequester().getId().equals(currentProfileId)) {
            throw new RuntimeException("user cant reject their own match request");
        }

        // match ended
        match.setStatus(Match.MatchStatus.ENDED);
        matchRepository.save(match);
    }

}


