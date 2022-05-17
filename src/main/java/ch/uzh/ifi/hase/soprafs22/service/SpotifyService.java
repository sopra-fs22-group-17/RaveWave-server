package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.net.URI;

import static ch.uzh.ifi.hase.soprafs22.spotify.GetPlaylistsItems.fetchPlaylistsItems;
import static ch.uzh.ifi.hase.soprafs22.spotify.GetUserSaveTracks.fetchUserSaveTracks;
import static ch.uzh.ifi.hase.soprafs22.spotify.GetUserTopTracks.fetchUsersTopTracks;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCode.authorizationCode_Sync;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCodeRefresh.authorizationCodeRefresh_Sync;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCodeUri.authorizationCodeUri_Sync;

@Service
@Transactional
public class SpotifyService {
    private static final String clientId = "d7d44473ad6a47cd86c580fcee015449";

    private static final String clientSecret = System.getenv("clientSecret");

    private static final URI redirectUri = SpotifyHttpManager
            .makeUri("http://localhost:3000/selectgamemode");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    public String authorizationCodeUri() {
        return authorizationCodeUri_Sync(spotifyApi);
    }

    public void authorizationCodeRefresh(RaveWaver raveWaver) {
        String oldAccessToken = raveWaver.getSpotifyToken();
        String refreshToken = raveWaver.getSpotifyRefreshToken();

        spotifyApi.setAccessToken(oldAccessToken);
        spotifyApi.setRefreshToken(refreshToken);
        authorizationCodeRefresh_Sync(spotifyApi);

        raveWaver.setSpotifyToken(spotifyApi.getAccessToken());
        raveWaver.setSpotifyRefreshToken(spotifyApi.getRefreshToken());

    }

    public void authorizationCode(SpotifyPostDTO spotifyPostDTO) {
        authorizationCode_Sync(spotifyApi, spotifyPostDTO.getCode());
    }


    public PlaylistTrack[] getPlaylistsItems(String playlistId) {
        return fetchPlaylistsItems(spotifyApi, playlistId);
    }

    public String getRaveWaverProfilePicture() throws IOException, ParseException, SpotifyWebApiException {
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        User info = getCurrentUsersProfileRequest.execute();
        return info.getImages()[0].getUrl();
    }

    public String getAccessToken() {
        return spotifyApi.getAccessToken();
    }

    public Track[] getPersonalizedPlaylistsItems(Long raveWaverId) {
        return fetchUsersTopTracks(spotifyApi);

    }

    public SavedTrack[] getSavedTrackItems(Long raveWaverId) {
        return fetchUserSaveTracks(spotifyApi);
    }

    public String getRefreshToken() {
        return spotifyApi.getRefreshToken();
    }


    public String generateProfilePicture(RaveWaver raveWaver) throws IOException, ParseException, SpotifyWebApiException {
        spotifyApi.setAccessToken(raveWaver.getSpotifyToken());
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        User info = getCurrentUsersProfileRequest.execute();

        if (info.getImages().length == 0) {
            return ("https://robohash.org/" + raveWaver.getUsername() + ".png");
        }
        return info.getImages()[0].getUrl();
    }

    public String getArtistProfilePicture(String id) throws IOException, ParseException, SpotifyWebApiException {
        GetArtistRequest.Builder getArtistRequest = spotifyApi.getArtist(id);
        GetArtistRequest built = getArtistRequest.build();
        Artist artist = built.execute();
        if (artist.getImages().length == 0) {
            return "";
        }
        return artist.getImages()[1].getUrl();
    }
}
