package ch.uzh.ifi.hase.soprafs22.spotify;

import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import java.io.IOException;

public class GetPlaylistsItems {
    private GetPlaylistsItems() {
    }

    public static PlaylistTrack[] fetchPlaylistsItems(SpotifyApi spotifyApi, String playlistId) {

        final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistId)
                // .fields("description")
                .limit(50)
                // .offset(0)
                // .market(CountryCode.SE)
                // .additionalTypes("track,episode")
                .build();

        try {
            final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();

            return playlistTrackPaging.getItems();
        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "An error while fetching the song information occured: " + e.getMessage());
        }
    }
/*
    public static Track[] fetchUsersTopTracks(SpotifyApi spotifyApi) {
        final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                // .limit(10)
                // .offset(0)
                // .time_range("medium_term")
                .build();
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();

            return trackPaging.getItems();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "An error while fetching the song information occured: " + e.getMessage());
        }
    }
*/
}
