package com.example.demo.services;

import com.example.demo.enteties.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Optional;

import static java.nio.file.Files.setAttribute;

@Service
public class UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    //User session object to bind data too and recall data from.
    @Autowired
    private HttpSession httpSession;

    @Autowired
    MatchService matchService;

    /**
     * Handles the OAuth2 login process.
     * If the user already exists in the database (found by Google ID), it loads their info.
     * Otherwise, creates a new user entry using the data returned by Google.
     *
     * ai-generated comment
     *
     * @param request the incoming OAuth2 login request
     * @return an authenticated OAuth2User with assigned roles and attributes
     * @throws OAuth2AuthenticationException if loading the user fails
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        String googleId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        Optional<User> optionalUser = userRepository.findByGoogleId(googleId);
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            httpSession.setAttribute("userId", user.getId());
        } else {
            user = new User();
            user.setGoogleId(googleId);
            user.setEmail(email);
            user.setName(name);
            user.setPictureUrl(picture);
            user.setRole("ROLE_USER");
            userRepository.save(user);
            httpSession.setAttribute("userId", user.getId());
            httpSession.setAttribute("profileId",matchService.getCurrentUserProfileId());
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                oAuth2User.getAttributes(),
                "sub"
        );
    }

}