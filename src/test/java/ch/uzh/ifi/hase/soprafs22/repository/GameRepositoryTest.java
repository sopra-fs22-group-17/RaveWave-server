package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GameRepositoryTest {
    @MockBean
    private GameRepository gameRepository;

    private Game game;

    @MockBean
    private SpotifyService spotifyService;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        game = new Game(spotifyService, SongPool.SWITZERLAND, raveWaverRepository);
        GameRepository.addGame(1, game);
    }

    @Test
    public void addGameTest() {
        assertEquals(game, GameRepository.findByLobbyId(1));
    }

    @Test
    public void removeGameTest() {
        GameRepository.removeGame(1);
        assertThrows(ResponseStatusException.class, () -> GameRepository.findByLobbyId(1));

    }

}
