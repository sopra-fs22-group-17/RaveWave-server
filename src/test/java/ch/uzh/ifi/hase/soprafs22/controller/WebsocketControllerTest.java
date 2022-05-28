package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WebsocketControllerTest {

    @Value("${local.server.port}")
    private int port;
    @Autowired
    private WebSocketController webSocketController;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private PlayerService playerService;

    private WebSocketStompClient stompClient;
    private StompSession stompSession;
    private WsTestUtils wsTestUtils = new WsTestUtils();



    @Before
    public void setUp() throws Exception {
        System.out.println(port);
        String wsUrl = "ws://localhost:" + port + "/ws";
        stompClient = wsTestUtils.createWebSocketClient();
        stompSession = stompClient.connect(wsUrl, new WsTestUtils.MyStompSessionHandler()).get();
    }


    @Test
    public void connectsToSocket() throws Exception {

        assertThat(stompSession.isConnected()).isTrue();
    }

    @Test
    public void receivesMessageFromSubscribedQueue() throws Exception {

        //given
        CompletableFuture<String> resultKeeper = new CompletableFuture<>();
        int lobbyId = 1;
        stompSession.subscribe(
                "/topic/lobbies/"+lobbyId,
                new WsTestUtils.MyStompFrameHandler((payload) -> resultKeeper.complete(payload)));

        Thread.sleep(1000);

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();

        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);
        gameSettingsDTO.setGameRounds(10);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.EIGHT);
        gameSettingsDTO.setRoundDuration(RoundDuration.EIGHT);
        gameSettingsDTO.setSongPool(SongPool.LATIN);

        doNothing().when(gameService).updateGameSettings(gameSettingsDTO,lobbyId);


        Thread.sleep(1000);
        //when
        webSocketController.updateGameSettings(lobbyId, gameSettingsDTO);


        //then
        assertThat(resultKeeper.get(2, SECONDS)).isEqualTo(gameSettingsDTO.toString());
    }

    @After
    public void tearDown() throws Exception {
        stompSession.disconnect();
        stompClient.stop();
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e));
        }
    }

}


