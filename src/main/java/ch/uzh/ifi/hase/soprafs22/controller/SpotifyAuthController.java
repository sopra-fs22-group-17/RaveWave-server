package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
//import ch.uzh.ifi.hase.soprafs22.spotify.GetUsersFavoriteSongs;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpotifyAuthController {

    private SpotifyService spotifyService;

    SpotifyAuthController(SpotifyService spotifyService){
        this.spotifyService = spotifyService;

    }

    @GetMapping(value = "/Spotify/authorizationCodeUri", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String generateAuthorizationCodeUri() {
        return spotifyService.authorizationCodeUri();
    }

    //get code from spotify API-Login Page
    @PostMapping("/Spotify/authorizationCode")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SpotifyGetDTO getAuthorizationCode(@RequestBody SpotifyPostDTO spotifyPostDTO) {
        spotifyService.authorizationCode(spotifyPostDTO);

        SpotifyGetDTO response = new SpotifyGetDTO();
        response.setAccessToken(spotifyService.getAccessToken());

        return response;
    }
/*
    //TODO temporary mapping --> will be removed
    @GetMapping("/PlaylistItems")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloWorld() {
        return spotifyService.getPlaylistsItems("37i9dQZEVXbJiyhoAPEfMK")[0].toString();
    }

    //TODO temporary mapping --> will be removed
    @GetMapping("/Spotify/UsersFavorites")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void usersFavorites() {
        spotifyService.getUsersFavoriteSongs();
    }
*/
}


