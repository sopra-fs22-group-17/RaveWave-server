package ch.uzh.ifi.hase.soprafs22.spotify;


import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import java.io.IOException;


public class GetPlaylistsItems {
    public static PlaylistTrack[] fetchPlaylistsItems(SpotifyApi spotifyApi, String playlistId) {

        final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
                .getPlaylistsItems(playlistId)
//              .fields("description")
//              .limit(10)
//              .offset(0)
//              .market(CountryCode.SE)
//              .additionalTypes("track,episode")
                .build();

        try {
            final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();


            //System.out.println(playlistTrackPaging.getItems());
            return playlistTrackPaging.getItems();
        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

}
