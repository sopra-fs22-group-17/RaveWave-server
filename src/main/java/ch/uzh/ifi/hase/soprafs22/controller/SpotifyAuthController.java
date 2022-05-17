package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyAuthCodeGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class SpotifyAuthController {

    private final SpotifyService spotifyService;
    private final RaveWaverService raveWaverService;
    Logger log = LoggerFactory.getLogger(WebSocketController.class);

    SpotifyAuthController(SpotifyService spotifyService, RaveWaverService raveWaverService) {
        this.spotifyService = spotifyService;
        this.raveWaverService = raveWaverService;

    }

    @GetMapping(value = "/Spotify/authorizationCodeUri")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SpotifyAuthCodeGetDTO generateAuthorizationCodeUri() {
        SpotifyAuthCodeGetDTO URL = new SpotifyAuthCodeGetDTO();

        URL.setRedirectionURL(spotifyService.authorizationCodeUri());
        log.info("Spotify authorization URL generated");
        return URL;
    }

    //get code from spotify API-Login Page
    @PostMapping("/Spotify/authorizationCode")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SpotifyGetDTO getAuthorizationCode(@RequestBody SpotifyPostDTO spotifyPostDTO, HttpServletRequest token) throws IOException, ParseException, SpotifyWebApiException {

        spotifyService.authorizationCode(spotifyPostDTO);

        //set the authorizationToken of a RaveWaver if a RaveWaver is given
        if(token != null){
          raveWaverService.updateSpotifyToken(token, spotifyService);
        }

        spotifyService.authorizationCodeRefresh(raveWaverService.getRaveWaverByToken(token));

        SpotifyGetDTO response = new SpotifyGetDTO();
        response.setAccessToken(spotifyService.getAccessToken());
        log.info("Spotify access granted!");
        return response;
    }

}


