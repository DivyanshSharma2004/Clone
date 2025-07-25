package com.example.demo.controller;


import com.example.demo.enteties.User;
import com.example.demo.enteties.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    //two separate repositories to make code more modular
    //user repository
    private final UserRepository userRepository;
    //user profile repository
    private final UserProfileRepository profileRepository;
    @Autowired
    private UserService userService;

    /**
     * GET /home — User homepage
     *
     * Redirects to login if not authenticated. else it fetches the user
     * from the database using their Google ID and passes it to the thymeleaf model.
     */
    //TODO: low priority: refactor this to include more of the code in the service
    @GetMapping("/home")
    public String userHome(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
        //If user isnt authenticated redirect them
        if (oauthUser == null) {
            return "redirect:/login";
        }
        //else get google if from google auth
        String googleId = oauthUser.getName();
        //get user objet via google id
        User user = userRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        //create model and return
        //FIXME: not working rn
        model.addAttribute("user", user);
        return "private/user-home";  // Thymeleaf template
    }

    /**
     * GET /profile — Show profile page
     *
     * Looks up the current user's profile and loads it into the model.
     */
    @GetMapping("/profile")
    public String showProfilePage(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {

        String googleId = oauthUser.getName();
        User user = userRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = profileRepository.findByUser(user).orElse(null);
        model.addAttribute("profile", profile);
        return "private/profile";
    }

    /**
     * POST /profile — Saves profile data
     *
     * Creates or updates a user profile using form input: name, bio,
     * selected profile picture, and interests.
     * TODO: change interests into pre-fixed selections.
     */
    @PostMapping("/profile")
    @ResponseBody
    public ResponseEntity<?> saveProfile(
            @RequestParam("name") String name,
            @RequestParam("bio") String bio,
            @RequestParam("interests") String interestsStr,
            @RequestParam(value = "selectedImage", required = false) String selectedImage,
            @AuthenticationPrincipal OAuth2User oauthUser) {

        String googleId = oauthUser.getName(); // “sub”
        User user = userRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        String pictureUrl = null;
        if (selectedImage != null && !selectedImage.isEmpty()) {
            pictureUrl = "/images/profiles/" + selectedImage;
        }

        // Check if profile exists
        UserProfile profile = profileRepository.findByUser(user).orElse(new UserProfile());
        profile.setUser(user);
        profile.setName(name);
        profile.setBio(bio);
        profile.setProfilePic(pictureUrl);

        ArrayList <String> intreastsList = new ArrayList<>();
        intreastsList.add(interestsStr);
        profile.setInterests(intreastsList);
        profileRepository.save(profile);

        return ResponseEntity.ok("Profile saved");
    }
}