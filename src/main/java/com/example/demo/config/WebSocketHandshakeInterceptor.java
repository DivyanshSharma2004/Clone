package com.example.demo.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

@Component
//made with ai
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpSession httpSession = servletRequest.getServletRequest().getSession(false);
            if (httpSession != null) {
                UUID profileId = (UUID) httpSession.getAttribute("profileId"); // adjust key if needed
                if (profileId != null) {
                    attributes.put("profileId", profileId); // this will be available in ChannelInterceptor
                }
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // nothing needed here
    }
}
