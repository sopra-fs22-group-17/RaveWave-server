package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.utils.SpotifyFetchHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpotifyServiceTest {

    @Mock
    private RaveWaverRepository raveWaverRepository;

    @InjectMocks
    private SpotifyService spotifyService;

    private SpotifyFetchHelper sfh;

    private Player player;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        sfh = new SpotifyFetchHelper();
        player = new Player();
        player.setRaveWaverId(1L);
        player.setPlayerName("playerName");
    }

    @Test
    void trackToTrackListTest() throws Exception {
        Track[] tracks = sfh.getTopTracksFixtures();
        ArrayList<Song> songs = spotifyService.trackToTrackList(tracks, player.getRaveWaverId(),
                player.getPlayerName());
        assertEquals(songs.get(0).getTrack(), tracks[0]);
        assertEquals(songs.get(0).getPlayerName(), "[RW] " + player.getPlayerName());
        assertEquals(songs.get(0).getIdentiy(), player.getRaveWaverId());
    }

    @Test
    void savedTracktoTrackListTest() throws Exception {
        SavedTrack[] tracks = sfh.getSavedTracksFixtures();
        ArrayList<Song> songs = spotifyService.savedTracktoTrackList(tracks, player.getRaveWaverId(),
                player.getPlayerName());
        assertEquals(songs.get(0).getTrack(), tracks[0].getTrack());
        assertEquals(songs.get(0).getPlayerName(), "[RW] " + player.getPlayerName());
        assertEquals(songs.get(0).getIdentiy(), player.getRaveWaverId());
    }

    @Test
    void playlistTrackToTrackListTest() throws Exception {
        PlaylistTrack[] tracks = sfh.getPlaylistFixtures();
        ArrayList<Song> songs = spotifyService.playlistTrackToTrackList(tracks);
        assertEquals(songs.get(0).getTrack(), tracks[0].getTrack());
    }

}
