package ch.uzh.ifi.hase.soprafs22.spotify;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;

public class GetUserTopTracks {
    public static Track[] fetchUsersTopTracks(SpotifyApi spotifyApi) {
        final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                // .limit(10)
                // .offset(0)
                .time_range("medium_term")
                .build();
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();

            return trackPaging.getItems();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "An error while fetching the song information occured: " + e.getMessage());
        }
    }
}
