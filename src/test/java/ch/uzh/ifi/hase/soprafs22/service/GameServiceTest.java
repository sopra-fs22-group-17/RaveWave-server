package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GameServiceTest {
    @Mock
    GameRepository gameRepository;
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;
    @InjectMocks
    private GameService gameService;
    @MockBean
    private Game testGame;
    @MockBean
    private SpotifyService spotifyService;
    @Mock
    private Player testPlayer;
    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        testPlayer = new Player();
        testPlayer.setId(1L);
        testPlayer.setLobbyId(1L);
        testPlayer.setPlayerName("Test");
        //TODO NOT WORKING
        //  testGame = new Game(spotifyService, SongPool.COUNTRY, raveWaverRepository);

        Mockito.when(playerRepository.save(Mockito.any())).thenReturn(testPlayer);
    }

    @AfterEach
    void teardown() {
        GameRepository.removeGame(1);
    }

    @Test
    public void createNewLobbyTest() {
        assertEquals(gameService.createNewLobby(spotifyService), 1);
    }

    @Disabled
    @Test
    public void saveAnswerTest() {

        Mockito.when(playerRepository.findById(testPlayer.getlobbyId())).thenReturn(testPlayer);
        Answer testAnswer = new Answer();
        gameService.createNewLobby(spotifyService);
        playerService.addPlayer(testPlayer);
        GameRepository.addGame(1, testGame);
        gameService.saveAnswer(testAnswer, 1);

        assertEquals(testAnswer, testGame.getListOfAnswers().get(0));
    }

}
