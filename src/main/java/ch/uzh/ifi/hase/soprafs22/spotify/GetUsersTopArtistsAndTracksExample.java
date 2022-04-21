package ch.uzh.ifi.hase.soprafs22.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.personalization.GetUsersTopArtistsAndTracksRequest;
import se.michaelthelin.spotify.requests.data.personalization.interfaces.IArtistTrackModelObject;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetUsersTopArtistsAndTracksExample {
    private static final ModelObjectType type = ModelObjectType.ARTIST;


    public static void getUsersTopArtistsAndTracks_Sync(SpotifyApi spotifyApi) {
        final GetUsersTopArtistsAndTracksRequest<? extends IArtistTrackModelObject> getUsersTopArtistsAndTracksRequest = spotifyApi
                .getUsersTopArtistsAndTracks(type)
//          .limit(10)
//          .offset(0)
//          .time_range("medium_term")
                .build();
        try {
            final Paging<? extends IArtistTrackModelObject> artistPaging = getUsersTopArtistsAndTracksRequest.execute();

            System.out.println("Total: " + artistPaging.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}