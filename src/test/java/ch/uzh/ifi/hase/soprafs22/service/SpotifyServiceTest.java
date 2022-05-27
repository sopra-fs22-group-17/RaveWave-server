package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.catalina.User;
import org.apache.hc.core5.http.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.boot.test.mock.mockito.MockBean;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.spotify.authorization.AuthorizationCodeRefresh;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import se.michaelthelin.spotify.model_objects.specification.*;

public class SpotifyServiceTest {

    @InjectMocks
    private SpotifyService spotifyService;
    private SpotifyApi spotifyApi;
    @Mock
    private RaveWaverRepository raveWaverRepository;

    @BeforeEach
    void setup() {
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

    @Test
    public void getSavedTrackItemTest() {
        RaveWaver optRaveWaver = new RaveWaver();
        ArrayList<Song> songs = new ArrayList<>();
        optRaveWaver.setSpotifyToken("spotifytoken");
        Optional<RaveWaver> raveWaver = Optional.of(optRaveWaver);
        when(raveWaverRepository.findById(Mockito.anyLong())).thenReturn(raveWaver);
        when(spotifyService.savedTracktoTrackList(Mockito.any(), Mockito.anyLong(), Mockito.any())).thenReturn(songs);

        assertEquals(spotifyService.getSavedTrackItems(1L), songs);

    }
}
