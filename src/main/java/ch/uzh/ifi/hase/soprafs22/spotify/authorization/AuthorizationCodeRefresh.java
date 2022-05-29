package ch.uzh.ifi.hase.soprafs22.spotify.authorization;

import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;

import java.io.IOException;

public class AuthorizationCodeRefresh {

    public static void authorizationCodeRefresh_Sync(SpotifyApi spotifyApi) {
        final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            // System.out.println("Expires in: " +
            // authorizationCodeCredentials.getExpiresIn());
        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
            //System.out.println("Error: " + e.getMessage());
        }
    }

}
