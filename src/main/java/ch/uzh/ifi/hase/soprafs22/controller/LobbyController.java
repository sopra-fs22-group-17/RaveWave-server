package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyIdDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LobbyController {
    Logger log = LoggerFactory.getLogger(LobbyController.class);

    private final GameService gameService;
    private SpotifyService spotifyService;
    private PlayerService playerService;

    // private Game game;

    LobbyController(GameService gameService, SpotifyService spotifyService, PlayerService playerService) {
        this.gameService = gameService;
        this.spotifyService = spotifyService;
        this.playerService = playerService;
    }

    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyIdDTO createNewLobby() {
        LobbyIdDTO lobbyIdDTO = new LobbyIdDTO();
        lobbyIdDTO.setLobbyId(gameService.createNewLobby(spotifyService));
        return lobbyIdDTO;

        // return
        // DTOMapper.INSTANCE.convertEntityToQuestionDTO(gameService.startNextRound(1));
    }

    @PostMapping("/lobbies/{lobbyId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PlayerGetDTO createPlayer(@RequestBody PlayerPostDTO playerPostDTO, @PathVariable Long lobbyId,
            HttpServletResponse response) {
        Player playerToAdd = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);
        playerToAdd.setLobbyId(lobbyId);
        Player newPlayer = playerService.addPlayer(playerToAdd);

        response.addHeader("Authorization", "Basic" + playerToAdd.getToken());

        return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(newPlayer);
    }

}
