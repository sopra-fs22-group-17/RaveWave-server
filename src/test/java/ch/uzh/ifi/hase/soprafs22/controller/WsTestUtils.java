package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class WsTestUtils {

    private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    WebSocketStompClient createWebSocketClient() {
        /*
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new StringMessageConverter());*/

        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
        WebSocketClient transport = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient;
    }

    static class MyStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            log.info("Stomp client is connected");
            super.afterConnected(session, connectedHeaders);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            log.info("Exception: " + exception);
            super.handleException(session, command, headers, payload, exception);
        }
    }

    public static class MyStompFrameHandler implements StompFrameHandler {

        private final Consumer<String> frameHandler;

        public MyStompFrameHandler(Consumer<String> frameHandler) {
            this.frameHandler = frameHandler;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return GameSettingsDTO.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            GameSettingsDTO obj =  (GameSettingsDTO)payload;
            log.info("received message: {} with headers: {}", obj, headers);
            frameHandler.accept(payload.toString());
        }
    }
}