package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.ArtistGame;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.SpotifyFetchHelper;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

public class ArtistGameTest {

    private Question mockQuestion;

    private SpotifyFetchHelper sfh;

    private ArrayList<Song> songs;

    private Player player;

    private ArtistGame artistGame;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @Mock
    private SpotifyService spotifyService;

    @Mock
    private SpotifyService spotifyService2;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        spotifyService = new SpotifyService(raveWaverRepository);

        player = new Player();
        player.setRaveWaverId(1L);
        player.setPlayerName("playerName");

        mockQuestion = new Question();
        mockQuestion.setQuestion("Guess the song artist!");
        mockQuestion.setPreviewUrl(
                "https://p.scdn.co/mp3-preview/9c3a62c08079cea9d25fff6db9177a08360ff0d8?cid=774b29d4f13844c495f206cafdad9c86");
        mockQuestion.setGamemode(GameMode.ARTISTGAME);
        mockQuestion.setSongTitle("Dust");
        mockQuestion.setPlaybackDuration(PlaybackDuration.FOURTEEN);
        mockQuestion.setArtist("M|O|O|N");
        mockQuestion.setSpotifyLink("https://open.spotify.com/artist/0M2HHtY3OOQzIZxrHkbJLT");
        mockQuestion.setCurrentRound(4);
        mockQuestion.setTotalRounds(10);
        mockQuestion.setCoverUrl("https://i.scdn.co/image/ab67616d00001e0253bc7ff619726c8640616154");

        sfh = new SpotifyFetchHelper();
        PlaylistTrack[] tracks = sfh.getPlaylistFixtures();
        songs = spotifyService.playlistTrackToTrackList(tracks);
    }

    @Test
    public void generateQuestionTest() throws ParseException, SpotifyWebApiException, IOException {
        System.out.println(songs.get(0).getTrack().getName());
        ArtistGame artistGame = new ArtistGame(1, songs, spotifyService2);

        String profilePictureString = "https://i.scdn.co/image/9f05124de35d807b78563ea2ca69550325081747";

        when(spotifyService2.getArtistProfilePicture(Mockito.any())).thenReturn(profilePictureString);

        assertEquals(mockQuestion.getSongTitle(), artistGame.getQuestion().getSongTitle());
        assertEquals(mockQuestion.getPreviewUrl(), artistGame.getQuestion().getPreviewUrl());

    }

}