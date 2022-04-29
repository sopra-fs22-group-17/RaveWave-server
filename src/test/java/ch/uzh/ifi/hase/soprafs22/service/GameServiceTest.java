package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;

public class GameServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @InjectMocks
    private GameService gameService;

    private Game testGame;
    private SpotifyService spotifyService;

    @Mock
    private Player testPlayer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        spotifyService = new SpotifyService();
        testPlayer = new Player();
        testPlayer.setId(1L);
        testPlayer.setLobbyId(1L);
        testGame = new Game(spotifyService);

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

    @Test
    public void updateGameSettings() {
        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        GameRepository.addGame(1, testGame);
        gameSettingsDTO.setGameMode(GameMode.ALBUMCOVERGAME);
        gameSettingsDTO.setGameRounds(8);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.SIXTEEN);
        gameSettingsDTO.setRoundDuration(RoundDuration.SIXTEEN);
        gameSettingsDTO.setSongPool(SongPool.ROCK);

        gameService.createNewLobby(spotifyService);
        gameService.updateGameSettings(gameSettingsDTO, 1);

        // assertEquals(testGame.getGameMode(), gameSettingsDTO.getGameMode());
        // assertEquals(testGame.getGameRounds(), gameSettingsDTO.getGameRounds());
        // assertEquals(testGame.getPlaybackDuration(),
        // gameSettingsDTO.getPlayBackDuration());
        // assertEquals(testGame.getRoundDuration(),
        // gameSettingsDTO.getRoundDuration());
        // assertEquals(testGame.getSongPool(), gameSettingsDTO.getSongPool());

    }

}
