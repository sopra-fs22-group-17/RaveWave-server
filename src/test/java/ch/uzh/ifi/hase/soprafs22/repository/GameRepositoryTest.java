package ch.uzh.ifi.hase.soprafs22.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.server.ResponseStatusException;

public class GameRepositoryTest {

    private GameRepository gameRepository;

    private Game game;

    private SpotifyService spotifyService;

    @BeforeEach
    void setup() {
        spotifyService = new SpotifyService();
        game = new Game(spotifyService, SongPool.SWITZERLAND, GameMode.SONGTITLEGAME);
        GameRepository.addGame(1, game);
    }

    @Test
    public void addGameTest() {
        assertEquals(game, GameRepository.findByLobbyId(1));
    }

    @Test
    public void removeGameTest() {
        GameRepository.removeGame(1);
        assertThrows(ResponseStatusException.class,() -> GameRepository.findByLobbyId(1));

    }

}
