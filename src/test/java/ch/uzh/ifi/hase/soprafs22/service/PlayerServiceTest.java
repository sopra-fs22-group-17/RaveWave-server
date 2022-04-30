package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;

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
/*
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
*/
}
