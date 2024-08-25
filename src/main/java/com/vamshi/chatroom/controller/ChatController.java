package com.vamshi.chatroom.controller;

import com.vamshi.chatroom.model.Message;
import com.vamshi.chatroom.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // ConcurrentHashMap to keep track of online users
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    @MessageMapping("/message")
    public void receivePublicMessage(@Payload Message message) {
        // Handle public message
        log.info("Received public message: {}", message);
        if ( message.getStatus() == Status.JOIN) {
            onlineUsers.add(message.getSenderName());
            broadcastOnlineUsers();
        } else if (message.getStatus() == Status.LEAVE) {
            onlineUsers.remove(message.getSenderName());
            broadcastOnlineUsers();
        }
        simpMessagingTemplate.convertAndSend("/chatroom/public", message);
    }

    @MessageMapping("/private-message")
    public void receivePrivateMessage(@Payload Message message) {
        log.info("Received private message: {}", message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
    }

    private void broadcastOnlineUsers() {
        // Broadcast the list of online users to all connected clients
        simpMessagingTemplate.convertAndSend("/online-users", onlineUsers);
    }
}
