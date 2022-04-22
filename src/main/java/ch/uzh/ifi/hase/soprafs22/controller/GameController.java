package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import ch.uzh.ifi.hase.soprafs22.spotify.SpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import ch.uzh.ifi.hase.soprafs22.service.GameService;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {
    Logger log = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    private SpotifyService spotifyService;

    private Game game;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }


    //TODO temporary mapping --> will be removed
    @GetMapping(value = "/PlaylistItems", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloWorld() {
        return spotifyService.getPlaylistsItems("37i9dQZEVXbJiyhoAPEfMK")[0].toString();
    }

    //TODO temporary mapping --> will be removed
    @GetMapping(value = "/Spotify/UsersFavorites", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void usersFavorites() {
        spotifyService.getUsersFavoriteSongs();
    }


    //TODO make it return correct lobbyID
    @PostMapping(value = "/lobbies", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createNewLobby() {
        this.game = new Game(spotifyService,"37i9dQZEVXbJiyhoAPEfMK");

        return "1";
    }

    @GetMapping(value = "/Spotify/authorizationCodeUri", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String authorizationCodeUri() {
        this.spotifyService = new SpotifyService();
        return spotifyService.authorizationCodeUri();
    }

    //get code from spotify API-Login Page
    @PostMapping(value = "/Spotify/authorizationCode", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getCode(@RequestBody SpotifyPostDTO spotifyPostDTO) {
        spotifyService.authorizationCode(spotifyPostDTO);
        return spotifyService.getAccessToken();
    }

}
