package ch.uzh.ifi.hase.soprafs22.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AuthorizationCodeUri {
    private static final String clientId = "d7d44473ad6a47cd86c580fcee015449";
    private static final String clientSecret = "3254634bdf1a4a6a8b26edcdc8f74b21";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://www.google.ch");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
//          .scope("user-read-birthdate,user-read-email")
//          .show_dialog(true)
            .build();

    public static void authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("URI: " + uri.toString());
    }

    public static void main(String[] args) {
        authorizationCodeUri_Sync();
    }
}
