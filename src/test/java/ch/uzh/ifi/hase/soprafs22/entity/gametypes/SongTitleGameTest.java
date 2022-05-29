package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.SpotifyFetchHelper;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SongTitleGameTest {

    private Question mockQuestion;

    private SpotifyFetchHelper sfh;

    private ArrayList<Song> songs;

    private Player player;

    private SongTitleGame songTitleGame;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @Mock
    private SpotifyService spotifyService;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        spotifyService = new SpotifyService(raveWaverRepository);

        player = new Player();
        player.setRaveWaverId(1L);
        player.setPlayerName("playerName");

        mockQuestion = new Question();
        mockQuestion.setQuestion("Guess the song title");
        mockQuestion.setPreviewUrl(
                "https://p.scdn.co/mp3-preview/9c3a62c08079cea9d25fff6db9177a08360ff0d8?cid=774b29d4f13844c495f206cafdad9c86");
        mockQuestion.setGamemode(GameMode.ARTISTGAME);
        mockQuestion.setSongTitle("Dust");
        mockQuestion.setArtist("M|O|O|N");
        mockQuestion.setSpotifyLink("https://open.spotify.com/track/2BZYVqGyL1L1adBbq2ClVv");
        mockQuestion.setCurrentRound(0);
        mockQuestion.setTotalRounds(0);
        mockQuestion.setCoverUrl("https://i.scdn.co/image/ab67616d00001e0253bc7ff619726c8640616154");

        sfh = new SpotifyFetchHelper();
        PlaylistTrack[] tracks = sfh.getPlaylistFixtures();
        songs = spotifyService.playlistTrackToTrackList(tracks);
    }

    @Test
    public void generateQuestionTest() throws ParseException, SpotifyWebApiException, IOException {
        SongTitleGame songTitleGame = new SongTitleGame(1, songs);

        assertEquals(mockQuestion.getSongTitle(), songTitleGame.getQuestion().getSongTitle());
        assertEquals(mockQuestion.getPreviewUrl(), songTitleGame.getQuestion().getPreviewUrl());
        assertEquals(mockQuestion.getQuestion(), songTitleGame.getQuestion().getQuestion());
        assertEquals(mockQuestion.getSongTitle(), songTitleGame.getQuestion().getSongTitle());
        assertNull(songTitleGame.getQuestion().getPlaybackDuration());
        assertEquals(mockQuestion.getArtist(), songTitleGame.getQuestion().getArtist());
        assertEquals(mockQuestion.getSpotifyLink(), songTitleGame.getQuestion().getSpotifyLink());
        assertEquals(mockQuestion.getCurrentRound(), songTitleGame.getQuestion().getCurrentRound());
        assertEquals(mockQuestion.getTotalRounds(), songTitleGame.getQuestion().getTotalRounds());
        assertEquals(mockQuestion.getCoverUrl(), songTitleGame.getQuestion().getCoverUrl());
        assertNotNull(songTitleGame.getQuestion().getAnswers());
        assertNotEquals(0, songTitleGame.getQuestion().getCorrectAnswer());
        assertNotNull(songTitleGame.getQuestion().getPictures());

    }

}