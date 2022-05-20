package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;

@Disabled
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

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
        Mockito.verify(playerRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testPlayer.getId(), createdPlayer.getId());
        assertEquals(testPlayer.getPlayerName(), createdPlayer.getPlayerName());
        assertEquals(0, createdPlayer.getStreak());
        assertEquals(0, createdPlayer.getTotalScore());
        assertNotNull(createdPlayer.getToken());
        assertEquals(1L, testPlayer.getlobbyId());
    }

    @Test
    public void addPlayerPlayernameTakenInSameLobby() {
        // given
        playerService.addPlayer(testPlayer);
        Mockito.when(playerRepository.findByPlayerNameAndLobbyId(testPlayer.getPlayerName(), testPlayer.getlobbyId()))
                .thenReturn(testPlayer);
        assertThrows(ResponseStatusException.class, () -> playerService.addPlayer(testPlayer));
    }

}
