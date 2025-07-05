package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // Allow access to static files
                                .requestMatchers("/home/**").permitAll() // Allow access to public files
                                .requestMatchers("/login/**").permitAll() // Allow access to public files
                                .requestMatchers("/error/**").permitAll()
                                .requestMatchers("/services").permitAll()//FIXME: tempo disabled for testing
                                //.requestMatchers("/html/private/**").authenticated() // Restrict access to private files
                                //.requestMatchers("/html/private/**").permitAll()// Does not Requires login only useful for development
                                //.anyRequest().permitAll() // Optionally permit all other requests
                                .anyRequest().permitAll()//FIXME: tempo disabled for testing
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")  // Custom login page URL
                                .permitAll()  // Allow everyone to access the custom login page

                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")  // Ensure OAuth2 uses the custom login page as well
                                .defaultSuccessUrl("/services", true) // Where to redirect after successful login
                ).sessionManagement(sessionManagement -> //session based authority
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Create session if needed
                                .invalidSessionUrl("/login?invalid=true")  // Redirect to login if session is invalid
                                .maximumSessions(1)  // Limit to a single session per user
                                .expiredUrl("/login?expired=true")  // Redirect if session expires
                ).logout(logout -> logout
                        .logoutUrl("/logout")  // Custom logout URL
                        .logoutSuccessUrl("/logged-out") // Redirect to a logout confirmation page
                        .invalidateHttpSession(true)  // Invalidate session
                        .deleteCookies("JSESSIONID")  // Remove session cookies
                        .logoutSuccessUrl("/login?logout"));// Redirect after logout

                //.csrf(AbstractHttpConfigurer::disable)  // 🚨 Disable CSRF Protection
        return http.build();
    }
}


