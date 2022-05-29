package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.CurrentAnswersDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.QuestionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
    private final WsTestUtils wsTestUtils = new WsTestUtils();

    @BeforeEach
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
    public void updateGameSettingsTest() throws Exception {

        // given
        CompletableFuture<String> resultKeeper = new CompletableFuture<>();
        int lobbyId = 1;
        stompSession.subscribe(
                "/topic/lobbies/" + lobbyId,
                new WsTestUtils.MyStompFrameHandlerGameSettings((payload) -> resultKeeper.complete(payload)));

        Thread.sleep(1000);

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();

        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);
        gameSettingsDTO.setGameRounds(10);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.EIGHT);
        gameSettingsDTO.setRoundDuration(RoundDuration.EIGHT);
        gameSettingsDTO.setSongPool(SongPool.LATIN);

        doNothing().when(gameService).updateGameSettings(gameSettingsDTO, lobbyId);

        Thread.sleep(1000);
        // when
        webSocketController.updateGameSettings(lobbyId, gameSettingsDTO);

        // then
        assertThat(resultKeeper.get(2, SECONDS)).isEqualTo(gameSettingsDTO.toString());
    }

    @Test
    public void startGameTest() throws Exception {

        // given
        CompletableFuture<String> resultKeeper = new CompletableFuture<>();
        int lobbyId = 1;
        stompSession.subscribe(
                "/topic/lobbies/" + lobbyId,
                new WsTestUtils.MyStompFrameHandlerStartGame((payload) -> resultKeeper.complete(payload)));

        Thread.sleep(1000);

        QuestionDTO question = new QuestionDTO();
        question.setCurrentRound(1);
        question.setQuestion("This is a question!");
        question.setAnswers(null);
        question.setPlayBackDuration("TEN");
        question.setPreviewURL("ThisIsAPreviewURL");
        question.setSongTitle("Title");

        doNothing().when(gameService).startGame(lobbyId);

        given(gameService.startNextRound(lobbyId)).willReturn(question);

        Thread.sleep(1000);
        // when
        webSocketController.startGame(lobbyId);

        // then
        assertThat(resultKeeper.get(2, SECONDS)).isEqualTo(question.toString());
    }

    @Test
    public void saveAnswerTest() throws Exception {

        // given
        CompletableFuture<String> resultKeeper = new CompletableFuture<>();
        int lobbyId = 1;
        stompSession.subscribe(
                "/topic/lobbies/" + lobbyId,
                new WsTestUtils.MyStompFrameHandlerSaveAnswer((payload) -> resultKeeper.complete(payload)));

        Thread.sleep(1000);

        Answer answer = new Answer();
        answer.setPlayerId(1L);
        answer.setAnswerTime(1);
        answer.setplayerGuess(1);
        answer.setToken("THISISATOKEN");

        CurrentAnswersDTO currentAnswersDTO = new CurrentAnswersDTO();
        currentAnswersDTO.setCurrentAnswers(1);
        currentAnswersDTO.setExpectedAnswers(2);

        when(gameService.saveAnswer(answer, 1)).thenReturn(false);

        given(gameService.fillAnswers(lobbyId)).willReturn(currentAnswersDTO);

        Thread.sleep(1000);
        // when
        webSocketController.saveAnswer(lobbyId, 1, answer);

        // then
        assertThat(resultKeeper.get(2, SECONDS)).isEqualTo(currentAnswersDTO.toString());
    }

    @Test
    public void startNextRoundTest() throws Exception {

        // given
        CompletableFuture<String> resultKeeper = new CompletableFuture<>();
        int lobbyId = 1;
        stompSession.subscribe(
                "/topic/lobbies/" + lobbyId,
                new WsTestUtils.MyStompFrameHandlerStartNextRound((payload) -> resultKeeper.complete(payload)));

        Thread.sleep(1000);

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion("This is a question");
        questionDTO.setCurrentRound(1);
        questionDTO.setTotalRounds(10);
        questionDTO.setSongTitle("This is a song title");
        questionDTO.setPlayBackDuration("PlaybackDuration.EIGHT");
        questionDTO.setCurrentRound(1);
        questionDTO.setPreviewURL("This is a preview URL");

        given(gameService.startNextRound(lobbyId)).willReturn(questionDTO);

        // when
        webSocketController.startNextRound(lobbyId);

        // then
        assertThat(resultKeeper.get(2, SECONDS)).isEqualTo(questionDTO.toString());
    }

    @Test
    public void endRoundTest() throws Exception {

        // given
        CompletableFuture<String> resultKeeper = new CompletableFuture<>();
        int lobbyId = 1;
        stompSession.subscribe(
                "/topic/lobbies/" + lobbyId,
                new WsTestUtils.MyStompFrameHandlerEndRound((payload) -> resultKeeper.complete(payload)));

        Thread.sleep(1000);

        Thread.sleep(1000);
        // when

        LeaderboardDTO leaderboardDTO = new LeaderboardDTO();

        leaderboardDTO.setArtist("Artist");
        leaderboardDTO.setSongTitle("songTitle");
        leaderboardDTO.setCorrectAnswer("correctAnswer");
        leaderboardDTO.setCoverUrl("CoverUrl");
        leaderboardDTO.setGameOver(false);
        leaderboardDTO.setSpotifyLink("SpotifyLink");
        leaderboardDTO.setPlayers(null);

        given(gameService.endRound(lobbyId)).willReturn(leaderboardDTO);

        webSocketController.endRound((long) lobbyId);

        // then
        assertThat(resultKeeper.get(2, SECONDS)).isEqualTo(leaderboardDTO.toString());
    }

    @AfterEach
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
