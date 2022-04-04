package ch.uzh.ifi.hase.soprafs22.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;



public class SpotifyAuthController {

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8000/api/get-user-code/");
    private String code = "";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("Keys.CLIENT_ID.getKey()")
            .setClientSecret("Keys.CLIENT_SECRET.getKey()")
            .setRedirectUri(redirectUri)
            .build();

}


