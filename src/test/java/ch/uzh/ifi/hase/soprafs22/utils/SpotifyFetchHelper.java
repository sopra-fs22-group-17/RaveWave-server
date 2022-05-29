package ch.uzh.ifi.hase.soprafs22.utils;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.library.GetUsersSavedTracksRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

public class SpotifyFetchHelper {
    private final SpotifyApi spotifyApi;

    public SpotifyFetchHelper() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId("zyuxhfo1c51b5hxjk09x2uhv5n0svgd6g")
                .setClientSecret("zudknyqbh3wunbhcvg9uyvo7uwzeu6nne")
                .setRedirectUri(SpotifyHttpManager.makeUri("https://example.com/spotify-redirect"))
                .setAccessToken(
                        "taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk")
                .setRefreshToken(
                        "b0KuPuLw77Z0hQhCsK-GTHoEx_kethtn357V7iqwEpCTIsLgqbBC_vQBTGC6M5rINl0FrqHK-D3cbOsMOlfyVKuQPvpyGcLcxAoLOTpYXc28nVwB7iBq2oKj9G9lHkFOUKn")
                .build();
    }

    public Track[] getTopTracksFixtures() throws Exception {
        final GetUsersTopTracksRequest request = spotifyApi.getUsersTopTracks()
                .setHttpManager(TestUtil.MockedHttpManager.returningJson("GetUsersTopTracksRequest.json")).build();
        try {
            final Paging<Track> trackPaging = request.execute();
            return trackPaging.getItems();

        }
        catch (Exception e) {
            return null;
        }

    }

    public PlaylistTrack[] getPlaylistFixtures() throws Exception {
        final GetPlaylistsItemsRequest request = spotifyApi.getPlaylistsItems("3AGOiaoRXMSjswCLtuNqv5")
                .setHttpManager(TestUtil.MockedHttpManager.returningJson("GetPlaylistsItemsRequest.json")).build();
        try {
            final Paging<PlaylistTrack> playlistTrackPaging = request.execute();
            return playlistTrackPaging.getItems();

        }
        catch (Exception e) {
            return null;
        }
    }

    public SavedTrack[] getSavedTracksFixtures() throws Exception {
        final GetUsersSavedTracksRequest request = spotifyApi.getUsersSavedTracks()
                .setHttpManager(TestUtil.MockedHttpManager.returningJson("GetUsersSavedTracksRequest.json")).build();
        try {
            final Paging<SavedTrack> savedTrackPaging = request.execute();
            return savedTrackPaging.getItems();

        }
        catch (Exception e) {
            return null;
        }

    }

    public PlaylistTrack[] getRepeatedPlaylistFixtures() throws Exception {
        final GetPlaylistsItemsRequest request = spotifyApi.getPlaylistsItems("0MUBlJUXISyAO4eb71DC7Y")
                .setHttpManager(TestUtil.MockedHttpManager.returningJson("GetRepeatedPlaylistIItemsRequest.json")).build();
        try {
            final Paging<PlaylistTrack> playlistTrackPaging = request.execute();
            return playlistTrackPaging.getItems();

        }
        catch (Exception e) {
            return null;
        }
    }

}
