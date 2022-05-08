package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.service.WebSocketService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import org.mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class WebSocketServiceTest {

    @Mock
    private WebSocketService webSocketService;
    @InjectMocks
    private Game testLobby;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        testLobby = new Game(new SpotifyService(), SongPool.LATINO, GameMode.ARTISTGAME);
        GameRepository.addGame(200, testLobby);

    }

    @Test
    void updateGameSettingsTest(){

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);
        gameSettingsDTO.setGameRounds(2);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.TEN);
        gameSettingsDTO.setSongPool(SongPool.LATINO);
        gameSettingsDTO.setRoundDuration(RoundDuration.EIGHTEEN);

        webSocketService.sendMessageToClients("/topic/lobbies/200", gameSettingsDTO);
        Mockito.verify(webSocketService, Mockito.times(1)).sendMessageToClients(Mockito.matches("/topic/lobbies/200"), Mockito.any());
    }
}