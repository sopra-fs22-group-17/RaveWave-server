package ch.uzh.ifi.hase.soprafs22.controller.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.ArtistGame;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.SpotifyFetchHelper;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtistGameTest {

    private Question mockQuestion;

    private SpotifyFetchHelper sfh;

    private ArrayList<Song> songs;

    private Player player;

    private ArtistGame artistGame;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    private SpotifyService spotifyService;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        spotifyService = new SpotifyService(raveWaverRepository);

        player = new Player();
        player.setRaveWaverId(1L);
        player.setPlayerName("playerName");

        mockQuestion = new Question();
        mockQuestion.setQuestion("Guess the song artist!");
        mockQuestion.setPreviewUrl("https://p.scdn.co/mp3-preview/79c8c9edc4f1ced9dbc368f24374421ed0a33005");
        mockQuestion.setGamemode(GameMode.ARTISTGAME);
        mockQuestion.setSongTitle("Otra Vez (feat. J Balvin)");
        mockQuestion.setPlaybackDuration(PlaybackDuration.FOURTEEN);
        mockQuestion.setArtist("Zion & Lennox");
        mockQuestion.setSpotifyLink("https://open.spotify.com/track/7pk3EpFtmsOdj8iUhjmeCM");
        mockQuestion.setCurrentRound(4);
        mockQuestion.setTotalRounds(10);
        mockQuestion.setCoverUrl("https://i.scdn.co/image/9f05124de35d807b78563ea2ca69550325081747");

        sfh = new SpotifyFetchHelper();
        PlaylistTrack[] tracks = sfh.getPlaylistFixtures();
        songs = spotifyService.playlistTrackToTrackList(tracks);
    }

    @Disabled
    @Test
    public void generateQuestionTest() throws ParseException, SpotifyWebApiException, IOException {
        System.out.println(songs.get(0).getTrack().getArtists());
        ArtistGame artistGame = new ArtistGame(0, songs, spotifyService);
        String profilePictureString = "https://i.scdn.co/image/9f05124de35d807b78563ea2ca69550325081747";

        // when(spotifyService.getArtistProfilePicture(Mockito.any())).thenReturn(profilePictureString);

        artistGame.generateQuestion();

        assertEquals(mockQuestion.getSongTitle(), artistGame.getQuestion().getSongTitle());

    }

}