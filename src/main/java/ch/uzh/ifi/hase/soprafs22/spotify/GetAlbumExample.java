package ch.uzh.ifi.hase.soprafs22.spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetAlbumExample {
    private static final String accessToken = "BQBvcBIfn9-uYzoRay_nG1xhJDiD_khoUdeoADuGOU8k4l3LUKSsDpjYBjhuZzr1nhvv_TpNX5ls_fszX02-Vdi4r3nddfloOO1KRqdDqF4kCWC-s38atco-DcyvpBES8-9lqc59CyK5ITauBELO24lJjgkK";
    private static final String id = "5zT1JLIj9E57p3e1rFm9Uq";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(id)
//          .market(CountryCode.SE)
            .build();

    public static void getAlbum_Sync() {
        try {
            final Album album = getAlbumRequest.execute();

            System.out.println("Name: " + album.getName());
        }
        catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getAlbum_Async() {
        try {
            final CompletableFuture<Album> albumFuture = getAlbumRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final Album album = albumFuture.join();

            System.out.println("Name: " + album.getName());
        }
        catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        }
        catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getAlbum_Sync();
        getAlbum_Async();
    }
}
