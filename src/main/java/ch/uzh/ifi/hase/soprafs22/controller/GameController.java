package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import ch.uzh.ifi.hase.soprafs22.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class GameController {
    Logger log = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    private SpotifyService spotifyService;

    //private Game game;

    GameController(GameService gameService, SpotifyService spotifyService) {
        this.gameService = gameService;
        this.spotifyService = spotifyService;
    }


    //TODO make it return correct lobbyID
    //TODO requestbody??
    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public int createNewLobby() {
        gameService.createNewLobby(spotifyService);

        //return DTOMapper.INSTANCE.convertEntityToQuestionDTO(gameService.startNextRound(1));
        return 1;
    }



}
