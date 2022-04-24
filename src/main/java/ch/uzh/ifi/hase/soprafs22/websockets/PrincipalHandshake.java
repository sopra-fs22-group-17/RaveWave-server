package ch.uzh.ifi.hase.soprafs22.websockets;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class PrincipalHandshake extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wshandler,
                                      Map<String, Object> attributes){

        //UUID.randomUUID().toString()
        return new StompPrincipal("1");
    }

}
