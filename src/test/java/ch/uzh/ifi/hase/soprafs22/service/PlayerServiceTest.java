package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.annotations.UpdateTimestamp;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player testPlayer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
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
        assertEquals(testPlayer.getScore(), 0);
        assertEquals(testPlayer.getStreak(), 0);
        assertNotNull(createdPlayer.getToken());
    }

    @Test
    public void addPlayerPlayernameTaken() {
        Player createdPlayer = playerService.addPlayer(testPlayer);
        Mockito.verify(playerRepository, Mockito.times(1)).save(Mockito.any());

        assertThrows(ResponseStatusException.class, () -> playerService.addPlayer(testPlayer));
    }

}
