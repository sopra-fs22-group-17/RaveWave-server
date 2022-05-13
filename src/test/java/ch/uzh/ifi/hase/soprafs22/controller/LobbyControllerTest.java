package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.BeforeMapping;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyIdDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@WebMvcTest(LobbyController.class)
public class LobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Player player;
    private Game game;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private GameService gameService;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @MockBean
    private SpotifyService spotifyService;

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
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }

    @BeforeEach
    void setup() {
        player = new Player();
        player.setPlayerName("playerName");
        player.setLobbyId(1L);

        game = new Game(spotifyService, SongPool.ROCK, raveWaverRepository);
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

    @Test
    public void createPlayerPOSTTest() throws Exception {
    PlayerPostDTO playerPostDTO = new PlayerPostDTO();
    playerPostDTO.setPlayerName("playerName");

    given(playerService.addPlayer(Mockito.any())).willReturn(player);

    MockHttpServletRequestBuilder postRequest = post("/lobbies/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(playerPostDTO));

    mockMvc.perform(postRequest).andExpect(status().isCreated())
            .andExpect(jsonPath("$.playerName", is(player.getPlayerName())));

     }

}