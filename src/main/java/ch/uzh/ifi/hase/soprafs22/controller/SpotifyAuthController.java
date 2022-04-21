package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
//import ch.uzh.ifi.hase.soprafs22.spotify.GetUsersFavoriteSongs;
import ch.uzh.ifi.hase.soprafs22.spotify.SpotifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

@RestController
public class SpotifyAuthController {
    private SpotifyService spotifyService;

    @GetMapping(value = "/PlaylistItems", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloWorld() {
        return spotifyService.getPlaylistsItems()[0].getTrack().getName();
    }

    @GetMapping(value = "/Spotify/UsersFavorites", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void usersFavorites() {
        spotifyService.getUsersFavoriteSongs();
    }

    @GetMapping(value = "/Spotify/authorizationCodeUri", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String authorizationCodeUri() {
        this.spotifyService = new SpotifyService();
        return spotifyService.authorizationCodeUri();
    }
    //TODO: DTO
    //get code from spotify API-Login Page
    @PostMapping(value = "/Spotify/authorizationCode", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SpotifyPostDTO getCode(@RequestBody SpotifyPostDTO spotifyPostDTO) {
        spotifyService.authorizationCode(spotifyPostDTO);
        return spotifyPostDTO;
    }
}


