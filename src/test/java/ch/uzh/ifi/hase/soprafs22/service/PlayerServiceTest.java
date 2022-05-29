package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @InjectMocks
    private GameService gameService;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @Mock
    private WebSocketService webSocketService;

    private Player testPlayer;

    @BeforeEach
    public void setup() {
        SpotifyService spotifyService = new SpotifyService(raveWaverRepository);
        Game game = new Game(spotifyService, SongPool.SWITZERLAND, raveWaverRepository);

        MockitoAnnotations.openMocks(this);
        GameRepository.addGame(1, game);
        // given
        testPlayer = new Player();
        testPlayer.setPlayerName("test");
        testPlayer.setLobbyId(1L);
        testPlayer.setId(1L);
        Mockito.when(playerRepository.save(Mockito.any())).thenReturn(testPlayer);
    }

    @Test
    void addPlayerSuccess() {
        Player createdPlayer = playerService.addPlayer(testPlayer);

        assertEquals(testPlayer.getId(), createdPlayer.getId());
        assertEquals(testPlayer.getPlayerName(), createdPlayer.getPlayerName());
        assertEquals(0, createdPlayer.getStreak());
        assertEquals(0, createdPlayer.getTotalScore());
        assertNotNull(createdPlayer.getToken());
        assertEquals(1L, testPlayer.getlobbyId());
    }

    @Test
    void addPlayerPlayerNameTakenInSameLobby() {
        // given
        playerService.addPlayer(testPlayer);
        Mockito.when(playerRepository.findByPlayerNameAndLobbyId(testPlayer.getPlayerName(), testPlayer.getlobbyId()))
                .thenReturn(testPlayer);
        assertThrows(ResponseStatusException.class, () -> playerService.addPlayer(testPlayer));
    }

    @Test
    void likedGameUnlockedArtistGameTest() {
        assertTrue(playerService.likedGameModeUnlocked(1L));
    }

    @Test
    void likedGameUnlockedLikedGameTest() {
        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setRoundDuration(RoundDuration.EIGHTEEN);
        gameSettingsDTO.setGameRounds(12);
        gameSettingsDTO.setGameMode(GameMode.LIKEDSONGGAME);
        gameSettingsDTO.setSongPool(SongPool.LATIN);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.EIGHT);

        gameService.updateGameSettings(gameSettingsDTO, 1);

        List<Player> players = new ArrayList<>();
        testPlayer.setRaveWaverId(1L);
        players.add(testPlayer);
        playerService.addPlayer(testPlayer);

        Mockito.when(playerRepository.findByLobbyId(1L)).thenReturn(players);

        assertFalse(playerService.likedGameModeUnlocked(1L));
    }


}
