package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

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
            .makeUri(System.getenv("redirectURL"));
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();
    private final RaveWaverRepository raveWaverRepository;

    public SpotifyService(@Qualifier("raveWaverRepository") RaveWaverRepository raveWaverRepository) {
        this.raveWaverRepository = raveWaverRepository;
    }

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

    public ArrayList<Song> getPlaylistsItems(String playlistId) {
        ArrayList<Song> songs = playlistTrackToTrackList(fetchPlaylistsItems(spotifyApi, playlistId));
        songs.removeIf(song -> song.getTrack().getPreviewUrl() == null);
        return songs;
    }

    public String getRaveWaverProfilePicture() throws IOException, ParseException, SpotifyWebApiException {
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        User info = getCurrentUsersProfileRequest.execute();
        return info.getImages()[0].getUrl();
    }

    public String getAccessToken() {
        return spotifyApi.getAccessToken();
    }

    public ArrayList<Song> getPersonalizedPlaylistsItems(Long raveWaverId) {
        Optional<RaveWaver> opRaveWaver = raveWaverRepository.findById(raveWaverId);
        if (opRaveWaver.isPresent()) {
            RaveWaver raveWaver = opRaveWaver.get();
            spotifyApi.setAccessToken(raveWaver.getSpotifyToken());
            ArrayList<Song> songs = trackToTrackList(fetchUsersTopTracks(spotifyApi), raveWaverId,
                    raveWaver.getUsername());
            songs.removeIf(song -> song.getTrack().getPreviewUrl() == null);
            return songs;
        }
        return null;
    }

    public ArrayList<Song> getSavedTrackItems(Long raveWaverId) {
        Optional<RaveWaver> opRaveWaver = raveWaverRepository.findById(raveWaverId);
        if (opRaveWaver.isPresent()) {
            RaveWaver raveWaver = opRaveWaver.get();
            spotifyApi.setAccessToken(raveWaver.getSpotifyToken());
            ArrayList<Song> songs = savedTracktoTrackList(fetchUserSaveTracks(spotifyApi), raveWaverId,
                    raveWaver.getUsername());
            songs.removeIf(song -> song.getTrack().getPreviewUrl() == null);
            return songs;
        }
        return null;
    }

    public ArrayList<Song> savedTracktoTrackList(SavedTrack[] savedTracks, long raveWaverId, String playerName) {
        ArrayList<Song> songs = new ArrayList<>();
        for (SavedTrack sTrack : savedTracks) {
            songs.add(new Song(sTrack.getTrack(), raveWaverId, "[RW] " + playerName));
        }
        return songs;
    }

    public ArrayList<Song> playlistTrackToTrackList(PlaylistTrack[] playlistsItems) {
        ArrayList<Song> songs = new ArrayList<>();
        for (PlaylistTrack pTrack : playlistsItems) {
            songs.add(new Song((Track) pTrack.getTrack()));
        }
        return songs;
    }

    public ArrayList<Song> trackToTrackList(Track[] personalizedPlaylistsItems, long raveWaverId, String playerName) {
        ArrayList<Song> songs = new ArrayList<>();
        for (Track track : personalizedPlaylistsItems) {
            songs.add(new Song(track, raveWaverId, "[RW] " + playerName));
        }
        return songs;

    }

    public String getRefreshToken() {
        return spotifyApi.getRefreshToken();
    }

    public String generateProfilePicture(RaveWaver raveWaver)
            throws IOException, ParseException, SpotifyWebApiException {
        spotifyApi.setAccessToken(raveWaver.getSpotifyToken());
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        User info;
        try {
            info = getCurrentUsersProfileRequest.execute();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Something went wrong when fetching the profile picture. Are you using a Spotify-Premium Account? "
                            + e.getMessage());
        }

        if (info.getImages().length == 0) {
            return ("https://api.multiavatar.com/" + raveWaver.getUsername() + ".png");
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
