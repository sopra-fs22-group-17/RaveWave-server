package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.FourRaveWaversConnectedDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyIdDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LobbyController.class)
class LobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Player player;

    @MockBean
    private Game game;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private GameService gameService;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private SpotifyService spotifyService;

    @MockBean
    private RaveWaverService raveWaverService;

    @Mock
    private GameRepository gamepRepository;

    @MockBean
    private HttpServletResponse response;

    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input
     * can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     *
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e));
        }
    }

    @BeforeEach
    void setup() {
        player = new Player();
        player.setPlayerName("playerName");
        player.setLobbyId(1L);

        // game = new Game(spotifyService, SongPool.ROCK, raveWaverRepository);
        // GameRepository.addGame(1, game);

    }

    @Test
    public void createLobbyPOSTTest() throws Exception {
        LobbyIdDTO lobbyIdDTO = new LobbyIdDTO();
        lobbyIdDTO.setLobbyId(1);

        given(gameService.createNewLobby(spotifyService)).willReturn(1);

        MockHttpServletRequestBuilder postRequest = post("/lobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(lobbyIdDTO));

        mockMvc.perform(postRequest).andExpect(status().isCreated()).andExpect(jsonPath("$.lobbyId", is(1)));
    }

    // TODO: Thinks game has already started.
    @Test
    public void createPlayerPOSTTest() throws Exception {
        PlayerPostDTO playerPostDTO = new PlayerPostDTO();
        playerPostDTO.setPlayerName("PlayerName");
        //playerPostDTO.setRaveWaverId(1L);


        Player player = new Player();
        player.setPlayerName("Name");
        player.setId(1L);
        player.setCorrectAnswers(1);
        player.setRaveWaverId(1L);
        player.setProfilePicture("ThisIsAPicture");
        player.setRoundScore(1000);
        player.setStreak(1);
        player.setToken("Tokenerino");
        player.setTotalScore(1000);

        given(playerService.addPlayer(Mockito.any())).willReturn(player);

        doNothing().when(response).addHeader(Mockito.any(), Mockito.any());

        //TODO greet testen?
        doNothing().when(playerService).greetPlayers(player);


        MockHttpServletRequestBuilder postRequest = post("/lobbies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(playerPostDTO))
                .header("Authorization", "bla");

        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerName", is(player.getPlayerName())))
                .andExpect(jsonPath("$.lobbyId", is((int) player.getlobbyId())));

    }

    @Test
    public void unlocklikedSongs() throws Exception {
        FourRaveWaversConnectedDTO checkFRWC = new FourRaveWaversConnectedDTO();
        checkFRWC.setFourRaveWaversConnected(true);
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        List<Player> players = Arrays.asList(player, player1, player2, player3);

        when(playerRepository.findByLobbyId(Mockito.anyLong())).thenReturn(players);

        given(playerService.checkFourRaveWaversConnected(1L)).willReturn(true);

        MockHttpServletRequestBuilder getRequest = get("/lobbies/1/likedSongsUnlocked")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(checkFRWC));

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.fourRaveWaversConnected", is(checkFRWC.getFourRaveWaversConnected())));

    }

    @Test
    public void lockedlikedSongs() throws Exception {
        FourRaveWaversConnectedDTO checkFRWC = new FourRaveWaversConnectedDTO();
        checkFRWC.setFourRaveWaversConnected(false);
        Player player1 = new Player();

        List<Player> players = Arrays.asList(player, player1);

        when(playerRepository.findByLobbyId(Mockito.anyLong())).thenReturn(players);

        given(playerService.checkFourRaveWaversConnected(1L)).willReturn(false);

        MockHttpServletRequestBuilder getRequest = get("/lobbies/1/likedSongsUnlocked")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(checkFRWC));

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.fourRaveWaversConnected", is(checkFRWC.getFourRaveWaversConnected())));

    }

}