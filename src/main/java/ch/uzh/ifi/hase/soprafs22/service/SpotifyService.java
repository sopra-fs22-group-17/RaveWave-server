package ch.uzh.ifi.hase.soprafs22.service;

import static ch.uzh.ifi.hase.soprafs22.spotify.GetPlaylistsItems.fetchPlaylistsItems;
import static ch.uzh.ifi.hase.soprafs22.spotify.GetUserSaveTracks.fetchUserSaveTracks;
import static ch.uzh.ifi.hase.soprafs22.spotify.GetUserTopTracks.fetchUsersTopTracks;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCode.authorizationCode_Sync;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCodeUri.authorizationCodeUri_Sync;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Service
@Transactional
public class SpotifyService {
    private static final String clientId = "d7d44473ad6a47cd86c580fcee015449";

    private static final String clientSecret = System.getenv("clientSecret");

    private final RaveWaverRepository raveWaverRepository;

    public SpotifyService(@Qualifier("raveWaverRepository") RaveWaverRepository raveWaverRepository) {
        this.raveWaverRepository = raveWaverRepository;
    }

    private static final URI redirectUri = SpotifyHttpManager
            .makeUri("https://sopra-fs22-group17-clientv3.herokuapp.com/selectgamemode");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    public String authorizationCodeUri() {
        return authorizationCodeUri_Sync(spotifyApi);
    }

    /*
     * public void authorizationCodeRefresh() {
     * authorizationCodeRefresh_Sync(spotifyApi);
     * }
     */

    public void authorizationCode(SpotifyPostDTO spotifyPostDTO) {
        authorizationCode_Sync(spotifyApi, spotifyPostDTO.getCode());
    }

    /*
     * public void getUsersFavoriteSongs() {
     * GetUsersTopArtistsAndTracksExample.getUsersTopArtistsAndTracks_Sync(
     * spotifyApi);
     * }
     */

    public PlaylistTrack[] getPlaylistsItems(String playlistId) {
        return fetchPlaylistsItems(spotifyApi, playlistId);
    }

    public String getAccessToken() {
        return spotifyApi.getAccessToken();
    }

    public Track[] getPersonalizedPlaylistsItems(Long raveWaverId) {
        Optional<RaveWaver> opRaveWaver = raveWaverRepository.findById(raveWaverId);
        // if (opRaveWaver.isPresent()) {
        // RaveWaver raveWaver = opRaveWaver.get();
        // String accessToken = raveWaver.getSpotifyToken();
        // spotifyApi.setAccessToken(accessToken);
        return fetchUsersTopTracks(spotifyApi);
        // }
        // return null;
    }

    public SavedTrack[] getSavedTrackItems(Long raveWaverId) {
        return fetchUserSaveTracks(spotifyApi);
    }
}
