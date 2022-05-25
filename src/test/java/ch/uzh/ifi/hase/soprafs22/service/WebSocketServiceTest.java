package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

@Disabled
public class WebSocketServiceTest {

    @Mock
    private WebSocketService webSocketService;
    @InjectMocks
    private Game testLobby;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        testLobby = new Game(new SpotifyService(raveWaverRepository), SongPool.SWITZERLAND, raveWaverRepository);
        GameRepository.addGame(200, testLobby);

    }

    @Test
    void updateGameSettingsTest() {

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);
        gameSettingsDTO.setGameRounds(2);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.TEN);
        gameSettingsDTO.setSongPool(SongPool.SWITZERLAND);
        gameSettingsDTO.setRoundDuration(RoundDuration.EIGHTEEN);

        webSocketService.sendMessageToClients("/topic/lobbies/200", gameSettingsDTO);
        Mockito.verify(webSocketService, Mockito.times(1)).sendMessageToClients(Mockito.matches("/topic/lobbies/200"),
                Mockito.any());
    }
}