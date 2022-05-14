package ch.uzh.ifi.hase.soprafs22.service;

import static ch.uzh.ifi.hase.soprafs22.spotify.GetPlaylistsItems.fetchPlaylistsItems;
import static ch.uzh.ifi.hase.soprafs22.spotify.GetUserSaveTracks.fetchUserSaveTracks;
import static ch.uzh.ifi.hase.soprafs22.spotify.GetUserTopTracks.fetchUsersTopTracks;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCode.authorizationCode_Sync;
import static ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCodeUri.authorizationCodeUri_Sync;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
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

    public ArrayList<Song> getPlaylistsItems(String playlistId) {
        return playlistTrackToTrackList(fetchPlaylistsItems(spotifyApi, playlistId));
    }

    public String getAccessToken() {
        return spotifyApi.getAccessToken();
    }

    public ArrayList<Song> getPersonalizedPlaylistsItems(Long raveWaverId) {
        Optional<RaveWaver> opRaveWaver = raveWaverRepository.findById(raveWaverId);
        if (opRaveWaver.isPresent()) {
            RaveWaver raveWaver = opRaveWaver.get();
            // String accessToken = raveWaver.getSpotifyToken();
            // spotifyApi.setAccessToken(accessToken);
            return trackToTrackList(fetchUsersTopTracks(spotifyApi), raveWaverId, raveWaver.getUsername());
        }
        return null;
    }

    public ArrayList<Song> getSavedTrackItems(Long raveWaverId) {
        Optional<RaveWaver> opRaveWaver = raveWaverRepository.findById(raveWaverId);
        if (opRaveWaver.isPresent()) {
            RaveWaver raveWaver = opRaveWaver.get();
            // String accessToken = raveWaver.getSpotifyToken();
            // spotifyApi.setAccessToken(accessToken);
            return savedTracktoTrackList(fetchUserSaveTracks(spotifyApi), raveWaverId, raveWaver.getUsername());
        }
        return null;
    }

    private ArrayList<Song> savedTracktoTrackList(SavedTrack[] savedTracks, long raveWaverId, String playerName) {
        ArrayList<Song> songs = new ArrayList<>();
        for (SavedTrack sTrack : savedTracks) {
            songs.add(new Song(sTrack.getTrack(), raveWaverId, playerName));
        }
        return songs;
    }

    private ArrayList<Song> playlistTrackToTrackList(PlaylistTrack[] playlistsItems) {
        ArrayList<Song> songs = new ArrayList<>();
        for (PlaylistTrack pTrack : playlistsItems) {
            songs.add(new Song((Track) pTrack.getTrack()));
        }
        return songs;
    }

    private ArrayList<Song> trackToTrackList(Track[] personalizedPlaylistsItems, long raveWaverId, String playerName) {
        ArrayList<Song> songs = new ArrayList<>();
        for (Track track : personalizedPlaylistsItems) {
            songs.add(new Song(track, raveWaverId, playerName));
        }
        return songs;

    }

}
