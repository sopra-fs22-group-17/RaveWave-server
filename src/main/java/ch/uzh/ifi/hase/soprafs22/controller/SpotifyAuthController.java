package ch.uzh.ifi.hase.soprafs22.controller;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;


public class SpotifyAuthController {

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8000/api/get-user-code/");
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("Keys.CLIENT_ID.getKey()")
            .setClientSecret("Keys.CLIENT_SECRET.getKey()")
            .setRedirectUri(redirectUri)
            .build();
    private final String code = "";

}


