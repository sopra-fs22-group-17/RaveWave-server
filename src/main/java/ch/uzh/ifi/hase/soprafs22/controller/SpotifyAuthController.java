package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyAuthCodeGetDTO;
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

    @GetMapping(value = "/Spotify/authorizationCodeUri")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SpotifyAuthCodeGetDTO generateAuthorizationCodeUri() {
        SpotifyAuthCodeGetDTO URL = new SpotifyAuthCodeGetDTO();
        SpotifyGetDTO test = new SpotifyGetDTO();
        test.setAccessToken(spotifyService.authorizationCodeUri());

        URL.setRedirectionURL(spotifyService.authorizationCodeUri());
        return URL;
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

}


