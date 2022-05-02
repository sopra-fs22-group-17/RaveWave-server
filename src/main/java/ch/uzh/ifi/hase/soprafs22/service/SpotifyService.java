package ch.uzh.ifi.hase.soprafs22.service;


import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.net.URI;

import static ch.uzh.ifi.hase.soprafs22.spotify.GetPlaylistsItems.fetchPlaylistsItems;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCode.authorizationCode_Sync;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCodeUri.authorizationCodeUri_Sync;

@Service
@Transactional
public class SpotifyService {
    private static final String clientId = "d7d44473ad6a47cd86c580fcee015449";


    private static final String clientSecret = System.getenv("clientSecret");

    //clientSecret for localhost
    //private static final String clientSecret = "";


    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://sopra-fs22-group17-clientv2.herokuapp.com/selectgamemode");


    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    public String authorizationCodeUri() {
        return authorizationCodeUri_Sync(spotifyApi);
    }

    /*public void authorizationCodeRefresh() {
        authorizationCodeRefresh_Sync(spotifyApi);
    }*/


    public void authorizationCode(SpotifyPostDTO spotifyPostDTO) {
        authorizationCode_Sync(spotifyApi, spotifyPostDTO.getCode());
    }

    /*
    public void getUsersFavoriteSongs() {
        GetUsersTopArtistsAndTracksExample.getUsersTopArtistsAndTracks_Sync(spotifyApi);
    }*/

    public PlaylistTrack[] getPlaylistsItems(String playlistId) {
        return fetchPlaylistsItems(spotifyApi, playlistId);
    }

    public String getAccessToken() {
        return spotifyApi.getAccessToken();
    }
}
