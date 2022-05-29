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
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LikedSongGameTest {

    private Question mockQuestion;

    private SpotifyFetchHelper sfh;

    private ArrayList<Song> songs;

    private Player player;

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
        mockQuestion.setQuestion("Guess the liked song");
        mockQuestion.setPreviewUrl(
                "https://p.scdn.co/mp3-preview/a8c9419cf7e46b58647420bb511839aaf5e9e8be");
        mockQuestion.setGamemode(GameMode.ARTISTGAME);
        mockQuestion.setSongTitle("More Than You Know");
        mockQuestion.setArtist("Axwell /\\ Ingrosso");
        mockQuestion.setSpotifyLink("https://open.spotify.com/track/71bBFbfn2OBK5QwUJSLS44");
        mockQuestion.setCurrentRound(0);
        mockQuestion.setTotalRounds(0);
        mockQuestion.setCoverUrl("https://i.scdn.co/image/ed38596d8ed2b3fdf2ab9e823df4c5f29758d42f");

        sfh = new SpotifyFetchHelper();
        Track[] tracks = sfh.getTopTracksFixtures();
        songs = spotifyService.trackToTrackList(tracks, 1L, "one");

    }

    @Test
    public void generateQuestionTest() throws ParseException, SpotifyWebApiException, IOException {

        Player one = new Player();
        one.setRaveWaverId(1L);
        one.setPlayerName("one");
        Player two = new Player();
        two.setRaveWaverId(2L);
        two.setPlayerName("two");
        Player three = new Player();
        three.setRaveWaverId(3L);
        three.setPlayerName("three");
        List<Player> players = List.of(one, two, three);
        System.out.println(songs.get(0).getTrack().getName());
        LikedSongGame likedSongGame = new LikedSongGame(1, songs, players);

        assertEquals(mockQuestion.getSongTitle(), likedSongGame.getQuestion().getSongTitle());
        assertEquals(mockQuestion.getPreviewUrl(), likedSongGame.getQuestion().getPreviewUrl());
        assertEquals(mockQuestion.getQuestion(), likedSongGame.getQuestion().getQuestion());
        assertEquals(mockQuestion.getSongTitle(), likedSongGame.getQuestion().getSongTitle());
        assertNull(likedSongGame.getQuestion().getPlaybackDuration());
        assertEquals(mockQuestion.getArtist(), likedSongGame.getQuestion().getArtist());
        assertEquals(mockQuestion.getSpotifyLink(), likedSongGame.getQuestion().getSpotifyLink());
        assertEquals(mockQuestion.getCurrentRound(), likedSongGame.getQuestion().getCurrentRound());
        assertEquals(mockQuestion.getTotalRounds(), likedSongGame.getQuestion().getTotalRounds());
        assertEquals(mockQuestion.getCoverUrl(), likedSongGame.getQuestion().getCoverUrl());
        assertNotNull(likedSongGame.getQuestion().getAnswers());
        assertNotEquals(0, likedSongGame.getQuestion().getCorrectAnswer());
        assertNotNull(likedSongGame.getQuestion().getPictures());

    }

}