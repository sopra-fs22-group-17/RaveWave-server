package ch.uzh.ifi.hase.soprafs22.spotify;

import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;
import se.michaelthelin.spotify.requests.data.library.GetUsersSavedTracksRequest;

import java.io.IOException;

public class GetUserSaveTracks {
    public static SavedTrack[] fetchUserSaveTracks(SpotifyApi spotifyApi) {

        final GetUsersSavedTracksRequest getUsersSavedTracksRequest = spotifyApi.getUsersSavedTracks()
                // .limit(10)
                // .offset(0)
                // .market(CountryCode.SE)
                .build();
        try {
            final Paging<SavedTrack> savedTrackPaging = getUsersSavedTracksRequest.execute();

            return savedTrackPaging.getItems();

        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "An error while fetching the song information occured: " + e.getMessage());
        }

    }
}
