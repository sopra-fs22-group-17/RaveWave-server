package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyAuthCodeGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SpotifyAuthController {

    private final SpotifyService spotifyService;
    private final RaveWaverService raveWaverService;

    SpotifyAuthController(SpotifyService spotifyService, RaveWaverService raveWaverService) {
        this.spotifyService = spotifyService;
        this.raveWaverService = raveWaverService;

    }

    @GetMapping(value = "/Spotify/authorizationCodeUri")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SpotifyAuthCodeGetDTO generateAuthorizationCodeUri() {
        SpotifyAuthCodeGetDTO URL = new SpotifyAuthCodeGetDTO();
        //SpotifyGetDTO test = new SpotifyGetDTO();
        //test.setAccessToken(spotifyService.authorizationCodeUri());

        URL.setRedirectionURL(spotifyService.authorizationCodeUri());
        return URL;
    }

    //get code from spotify API-Login Page
    @PostMapping("/Spotify/authorizationCode")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SpotifyGetDTO getAuthorizationCode(@RequestBody SpotifyPostDTO spotifyPostDTO, HttpServletRequest token) {

        spotifyService.authorizationCode(spotifyPostDTO);

        if(token != null){
            raveWaverService.updateSpotifyToken(token, spotifyService);
        }

        SpotifyGetDTO response = new SpotifyGetDTO();
        response.setAccessToken(spotifyService.getAccessToken());

        return response;
    }

}


