package ch.uzh.ifi.hase.soprafs22.spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;

import java.io.IOException;

public class GetPlaylist {
    private static final String accessToken = "BQAnUJAuS0yLVO7G0fZea-a5qyRu2VTTbxS51ONMAOJ7f1ty1M0hBJ14I_4KqR_HUyffqniBcFfV4Rc2Z-Wq8KrJAE3vd-qs1DnddsEJdMk3QMNgPfiLi2B0IMC8Xu5GnkYP-2Z1-A69EWyuJZQXkXTHtZfx";
    private static final String playlistId = "3AGOiaoRXMSjswCLtuNqv5";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();

    private static final GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(playlistId)
//          .fields("description")
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
            .build();

    public static Playlist fetchPlaylist() {
        try {
            final Playlist playlist = getPlaylistRequest.execute();

            return playlist;
        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(fetchPlaylist().getTracks());
    }
}
