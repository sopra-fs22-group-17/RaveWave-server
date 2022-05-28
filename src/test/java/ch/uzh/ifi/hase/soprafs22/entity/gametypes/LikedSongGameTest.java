package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.params.DisableIfAllArguments;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.SpotifyFetchHelper;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

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

        Player one = new Player();
        Player two = new Player();
        Player three = new Player();
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