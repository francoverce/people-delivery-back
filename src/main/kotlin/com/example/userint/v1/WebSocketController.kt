package com.example.userint.v1

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class WebSocketController{

    @Autowired
    private lateinit var messagingTemplate: SimpMessageSendingOperations

    @MessageMapping("/sendUpdate")
    @SendTo("/topic/update")
    fun sendUpdate(message: String): String {

        val notification = "Nueva notificación: $message"

        messagingTemplate.convertAndSend("/topic/update", notification)

        return notification
    }
}
