package ch.uzh.ifi.hase.soprafs22.controller;

/*import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.service.WebSocketService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.messaging.*;
import org.springframework.util.MimeTypeUtils;

public class WebSocketServiceTest {

    @Mock
    private GameService gameService;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PlayerService playerService;
    @Spy
    @InjectMocks
    private WebSocketService webSocketService;
    @InjectMocks
    private Game testLobby;
    @InjectMocks
    private GameSettingsDTO gameSettingsDTO;

    @BeforeEach
    void setup(){

        testLobby = new Game(new SpotifyService(), SongPool.LATINO);
        GameRepository.addGame(200, testLobby);
        //webSocketService = new WebSocketService(playerRepository, playerService);

    }

    @Test
    void updateGameSettingsTest(){

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);
        gameSettingsDTO.setGameRounds(2);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.TEN);
        gameSettingsDTO.setSongPool(SongPool.LATINO);
        gameSettingsDTO.setRoundDuration(RoundDuration.EIGHTEEN);

        webSocketService.sendMessageToClients("/topic/lobbies/200", gameSettingsDTO);
        Mockito.verify(webSocketService, Mockito.times(1)).sendMessageToClients(Mockito.matches("/topic/lobbies/200"), Mockito.any());
    }
}*/
/*

import ch.uzh.ifi.hase.soprafs22.Application;
import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.service.WebSocketService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class WebSocketServiceTest {

    static final String WEBSOCKET_URI = "ws://sopra-fs22-group17-server.herokuapp.com/ws";
    static final String WEBSOCKET_TOPIC = "/topic";

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    @BeforeEach
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);
        gameSettingsDTO.setGameRounds(2);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.TEN);
        gameSettingsDTO.setSongPool(SongPool.LATINO);
        gameSettingsDTO.setRoundDuration(RoundDuration.EIGHTEEN);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(gameSettingsDTO);
        session.send(WEBSOCKET_TOPIC, json.getBytes());

        Assert.assertEquals(json, blockingQueue.poll(1, SECONDS));
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
*/