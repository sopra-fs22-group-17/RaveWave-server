package ch.uzh.ifi.hase.soprafs22.spotify.authorization;

import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.io.IOException;

public class AuthorizationCode {


    public static void authorizationCode_Sync(SpotifyApi spotifyApi, String code) {
        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            //System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The authorization code is invalid: " + e.getMessage());
        }
    }

}