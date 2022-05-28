package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.CurrentAnswersDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
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

    public static class MyStompFrameHandlerGameSettings implements StompFrameHandler {

        private final Consumer<String> frameHandler;

        public MyStompFrameHandlerGameSettings(Consumer<String> frameHandler) {
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
    public static class MyStompFrameHandlerStartGame implements StompFrameHandler {

        private final Consumer<String> frameHandler;

        public MyStompFrameHandlerStartGame(Consumer<String> frameHandler) {
            this.frameHandler = frameHandler;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return QuestionDTO.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            QuestionDTO obj =  (QuestionDTO)payload;
            log.info("received message: {} with headers: {}", obj, headers);
            frameHandler.accept(payload.toString());
        }
    }
    public static class MyStompFrameHandlerSaveAnswer implements StompFrameHandler {

        private final Consumer<String> frameHandler;

        public MyStompFrameHandlerSaveAnswer(Consumer<String> frameHandler) {
            this.frameHandler = frameHandler;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return CurrentAnswersDTO.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            CurrentAnswersDTO obj =  (CurrentAnswersDTO)payload;
            log.info("received message: {} with headers: {}", obj, headers);
            frameHandler.accept(payload.toString());
        }
    }

    public static class MyStompFrameHandlerEndRound implements StompFrameHandler {

        private final Consumer<String> frameHandler;

        public MyStompFrameHandlerEndRound(Consumer<String> frameHandler) {
            this.frameHandler = frameHandler;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return LeaderboardDTO.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            LeaderboardDTO obj =  (LeaderboardDTO)payload;
            log.info("received message: {} with headers: {}", obj, headers);
            frameHandler.accept(payload.toString());
        }
    }

    public static class MyStompFrameHandlerStartNextRound implements StompFrameHandler {

        private final Consumer<String> frameHandler;

        public MyStompFrameHandlerStartNextRound(Consumer<String> frameHandler) {
            this.frameHandler = frameHandler;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return QuestionDTO.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            QuestionDTO obj =  (QuestionDTO)payload;
            log.info("received message: {} with headers: {}", obj, headers);
            frameHandler.accept(payload.toString());
        }
    }
}