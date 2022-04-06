package ch.uzh.ifi.hase.soprafs22.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import static ch.uzh.ifi.hase.soprafs22.spotify.GetPlaylistsItems.fetchPlaylistsItems;

public class SpotifyService {
    private static final String accessToken = "BQAaj-lzAbhmapRS58RRVg0BXxj_HyCIAZdYYS_u6avym2zxbCNPTWDb4P8QI-c7d_qF1T0m7gZzCbjohjjO9cAAhjSg9v6mhgphr9ankW6Dk8BRh_ns_SD4vLnin5DwxFbqVeiZoeVi_osHgjq4NliiVZXq";
    private static final String playlistId = "3AGOiaoRXMSjswCLtuNqv5";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();



    public PlaylistTrack[] getPlaylistsItems(){
        //TODO handle null exception
        return fetchPlaylistsItems(spotifyApi, playlistId);
    }
}
