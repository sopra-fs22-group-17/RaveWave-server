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
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.PlayerJoinDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
    public void addPlayerSuccess() {
        Player createdPlayer = playerService.addPlayer(testPlayer);

        assertEquals(testPlayer.getId(), createdPlayer.getId());
        assertEquals(testPlayer.getPlayerName(), createdPlayer.getPlayerName());
        assertEquals(0, createdPlayer.getStreak());
        assertEquals(0, createdPlayer.getTotalScore());
        assertNotNull(createdPlayer.getToken());
        assertEquals(1L, testPlayer.getlobbyId());
    }

    @Test
    public void addPlayerPlayerNameTakenInSameLobby() {
        // given
        playerService.addPlayer(testPlayer);
        Mockito.when(playerRepository.findByPlayerNameAndLobbyId(testPlayer.getPlayerName(), testPlayer.getlobbyId()))
                .thenReturn(testPlayer);
        assertThrows(ResponseStatusException.class, () -> playerService.addPlayer(testPlayer));
    }


    @Disabled
    @Test
    public void greetPlayersTest() {

        Player testPlayer2 = new Player();
        testPlayer2.setPlayerName("testPlayer2");
        testPlayer2.setLobbyId(1L);
        testPlayer2.setId(2L);

        List<Player> players = new ArrayList<>();
        players.add(testPlayer);
        players.add(testPlayer2);
        playerService.addPlayer(testPlayer);
        playerService.addPlayer(testPlayer2);
        playerService.greetPlayers(testPlayer);
        PlayerJoinDTO playerJoinDTO = new PlayerJoinDTO();
        playerJoinDTO.setName(testPlayer.getPlayerName());
        playerJoinDTO.setLikedGameModeUnlocked(true);

        Mockito.when(playerRepository.findByLobbyId(1L)).thenReturn(players);
        Mockito.when(playerService.likedGameModeUnlocked(1L)).thenReturn(true);
        Mockito.verify(webSocketService, Mockito.times(0)).sendMessageToClients(Mockito.matches("/topic/lobbies/1"),
                Mockito.any());
    }

    @Test
    public void likedGameUnlockedArtistGameTest() {
        assertTrue(playerService.likedGameModeUnlocked(1L));
    }

    @Test
    public void likedGameUnlockedLikedGameTest() {
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
