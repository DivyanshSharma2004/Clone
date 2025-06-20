package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class CustomErrorControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void testHandleError_404() throws Exception {
//        // Simulate a 404 error (Not Found) with a mock authenticated user
//        mockMvc.perform(get("/nonexistent-url")
//                        .with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("USER"))) // Simulate logged-in user
//                .andExpect(status().isNotFound())
//                .andExpect(view().name("public/error/404"));
//    }
//
//    @Test
//    void testHandleError_403() throws Exception {
//        // Simulate a 403 error (Forbidden) with a mock authenticated user
//        mockMvc.perform(get("/forbidden-url")
//                        .with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("USER"))) // Simulate logged-in user
//                .andExpect(status().isForbidden())
//                .andExpect(view().name("public/error/403"));
//    }
//
//    @Test
//    void testHandleError_500() throws Exception {
//        // Simulate a 500 error (Internal Server Error) with a mock authenticated user
//        mockMvc.perform(get("/server-error-url")
//                        .with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("USER"))) // Simulate logged-in user
//                .andExpect(status().isInternalServerError())
//                .andExpect(view().name("public/error/500"));
//    }
//
//    @Test
//    void testHandleError_General() throws Exception {
//        // Simulate some other error (you can customize this for other error codes)
//        mockMvc.perform(get("/error")
//                        .with(SecurityMockMvcRequestPostProcessors.user("testUser").roles("USER"))) // Simulate logged-in user
//                .andExpect(status().isInternalServerError())
//                .andExpect(view().name("public/error/general"));
//    }
}
