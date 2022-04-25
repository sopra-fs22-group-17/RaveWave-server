package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import ch.uzh.ifi.hase.soprafs22.service.GameService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
public class GameController {
    Logger log = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    private SpotifyService spotifyService;
    private PlayerService playerService;

    //private Game game;

    GameController(GameService gameService, SpotifyService spotifyService, PlayerService playerService) {
        this.gameService = gameService;
        this.spotifyService = spotifyService;
        this.playerService = playerService;
    }


    //TODO make it return correct lobbyID
    //TODO requestbody??
    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public int createNewLobby() {
        return gameService.createNewLobby(spotifyService);

        //return DTOMapper.INSTANCE.convertEntityToQuestionDTO(gameService.startNextRound(1));
    }

    //TODO multiple players with the same names in different lobbies
    @PostMapping("/lobbies/{lobbyId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PlayerGetDTO createPlayer(@RequestBody PlayerPostDTO playerPostDTO, HttpServletResponse response) {
        Player playerToAdd = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);
        Player newPlayer = playerService.addPlayer(playerToAdd);

        response.addHeader("Authorization", "Basic" + playerToAdd.getToken());

        return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(newPlayer);
    }

}
