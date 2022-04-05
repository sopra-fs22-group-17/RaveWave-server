package ch.uzh.ifi.hase.soprafs22.spotify;


import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import java.io.IOException;

public class GetPlaylistsItems {
    private final String playlistId = "3AGOiaoRXMSjswCLtuNqv5";
    private String accessToken;
    private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
            .getPlaylistsItems(playlistId)
//          .fields("description")
//          .limit(10)
//          .offset(0)
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
            .build();

    public GetPlaylistsItems(RaveWaver raveWaver) {
        accessToken = raveWaver.getSpotifyToken();
    }

    public static void main(String[] args) {

        RaveWaver testRaveWaver = new RaveWaver();

        testRaveWaver.setSpotifyToken("BQDD7e12NvOLbt5YgT6TkmGB6TqqODcrgFogcI6KAv1BjQVTlOgxxf7LuMluqBRrNDLMzmAw8OMyd-q9LRzG4WWIgAexKkzw_L7DLJ0fb0ilUaAPUPKZJS3aqlP2jVKS1eGbtkECxikAHg7aA3FCFHdSilIs");
        //getPlaylistsItems_Async();
    }

    private PlaylistTrack[] fetchPlaylistsItems() {
        try {
            final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
            return playlistTrackPaging.getItems();

        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
