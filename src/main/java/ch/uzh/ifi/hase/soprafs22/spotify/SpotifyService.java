package ch.uzh.ifi.hase.soprafs22.spotify;

import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.net.URI;

import static ch.uzh.ifi.hase.soprafs22.spotify.GetPlaylistsItems.fetchPlaylistsItems;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCodeUri.authorizationCodeUri_Sync;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCodeRefresh.authorizationCodeRefresh_Sync;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCode.authorizationCode_Sync;

public class SpotifyService {
    //private static final String accessToken = "BQAaj-lzAbhmapRS58RRVg0BXxj_HyCIAZdYYS_u6avym2zxbCNPTWDb4P8QI-c7d_qF1T0m7gZzCbjohjjO9cAAhjSg9v6mhgphr9ankW6Dk8BRh_ns_SD4vLnin5DwxFbqVeiZoeVi_osHgjq4NliiVZXq";
    private static final String clientId = "d7d44473ad6a47cd86c580fcee015449";
    private static final String clientSecret = "36ff7863a5494a1a83c214c9188288ca";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://www.google.ch");


    private static final String playlistId = "37i9dQZEVXbJiyhoAPEfMK";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    public String authorizationCodeUri(){
        return authorizationCodeUri_Sync(spotifyApi);
    }

    public void authorizationCodeRefresh(){
        authorizationCodeRefresh_Sync(spotifyApi);
    }

    public void authorizationCode(SpotifyPostDTO spotifyPostDTO) {
        authorizationCode_Sync(spotifyApi, spotifyPostDTO.getCode());
    }

    public void getUsersFavoriteSongs(){
        GetUsersTopArtistsAndTracksExample.getUsersTopArtistsAndTracks_Sync(spotifyApi);
    }

    public PlaylistTrack[] getPlaylistsItems(){
        //TODO handle null exception
        return fetchPlaylistsItems(spotifyApi, playlistId);
    }
}
