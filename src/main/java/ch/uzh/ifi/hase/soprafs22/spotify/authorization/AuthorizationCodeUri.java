package ch.uzh.ifi.hase.soprafs22.spotify.authorization;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

public class AuthorizationCodeUri {
    //private static final String clientId = "d7d44473ad6a47cd86c580fcee015449";
    //private static final String clientSecret = "36ff7863a5494a1a83c214c9188288ca";
    //private static final URI redirectUri = SpotifyHttpManager.makeUri("https://www.google.ch");


    public static String authorizationCodeUri_Sync(SpotifyApi spotifyApi) {

        final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
                .scope("user-read-private,playlist-read-private,user-library-read,user-top-read,user-read-recently-played")
//          .show_dialog(true)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();

        return uri.toString();
    }


}
