package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;/**
 * REST controller for handling test connections and simple data exchanges.
 * This controller allows for both POST and GET requests to test connectivity and data transfer.
 */
@CrossOrigin(origins = "*") // Allow cross-origin requests from any origin
@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * Endpoint for handling a test POST connection.
     *
     * This method receives raw data in the request body, prints it to the console,
     * and returns a confirmation response containing the received data.
     *
     * @param data The string data sent in the request body.
     * @return A string response confirming the received data.
     */
    @PostMapping("/connection")
    public String handleTestConnection(@RequestBody String data) {
        // Print the received data to the console
        System.out.println("Received data: " + data);

        // Return a simple response confirming the received data
        return "Received your test data: " + data;
    }

    /**
     * Endpoint for handling a test GET connection.
     *
     * This method returns a simple test response and logs the request.
     *
     * @return A string response indicating the test was successful.
     */
    @GetMapping("/getData")
    public String handleTestConnectionGET() {
        // Log the incoming GET request
        System.out.println("Received GET request");

        // Return a simple response
        return "Received your test data: liogma";
    }
}
