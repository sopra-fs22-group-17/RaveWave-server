package ch.uzh.ifi.hase.soprafs22.websockets;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.MessageDTO;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    // Handles messages from /app/chat. (Note the Spring adds the /app prefix for us).
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDTO getMessages(MessageDTO dto){

        return dto;

    }

}