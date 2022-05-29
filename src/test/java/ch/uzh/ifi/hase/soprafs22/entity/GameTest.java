package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.SpotifyFetchHelper;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Mock
    private SpotifyService spotifyService;

    private SpotifyService spotifyServiceNotMocked;
    @Mock
    private Player testPlayer;
    @Mock
    private Game testGame;

    @MockBean
    private RaveWaverRepository raveWaverRepository;

    @Mock
    private RaveWaverRepository raveWaverRepository2;

    @Mock
    private LeaderboardDTO leaderboardDTOMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        testGame = new Game(spotifyService, SongPool.SWITZERLAND, raveWaverRepository);
        testPlayer = new Player();
        testPlayer.setId(1L);
        testPlayer.setLobbyId(1L);
        testPlayer.setPlayerName("Test");
        testPlayer.setToken("token");


    }

    @Test
    public void updateGameSettingsTest() {

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);
        gameSettingsDTO.setGameRounds(10);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.EIGHTEEN);
        gameSettingsDTO.setSongPool(SongPool.SWITZERLAND);

        testGame.updateGameSettings(gameSettingsDTO);

        GameSettingsDTO actual = testGame.getGameSettings();

        assertEquals(gameSettingsDTO.getGameRounds(), actual.getGameRounds());
        assertEquals(gameSettingsDTO.getGameMode(), actual.getGameMode());
        assertEquals(gameSettingsDTO.getPlayBackDuration(), actual.getPlayBackDuration());
        assertEquals(gameSettingsDTO.getRoundDuration(), actual.getRoundDuration());
        assertEquals(gameSettingsDTO.getSongPool(), actual.getSongPool());
    }

    @Test
    public void fillLeaderboardTest() {
        Player player1 = new Player();
        player1.setPlayerName("TestPlayer");
        player1.setStreak(1);
        player1.setLobbyId(1L);
        player1.setRoundScore(2120);

        Player player2 = new Player();
        player1.setPlayerName("TestPlayer2");
        player1.setStreak(3);
        player1.setLobbyId(1L);
        player1.setRoundScore(10);

        List<Player> players = Arrays.asList(player1, player2);

        LeaderboardDTO leaderboard = testGame.fillLeaderboard(players);

        // given().willReturn(1);

        assertEquals(leaderboard.getPlayers().get(0).getPlayerId(), player1.getId());
        assertEquals(leaderboard.getPlayers().get(0).getPlayerName(), player1.getPlayerName());
        assertEquals(leaderboard.getPlayers().get(0).getRoundScore(), player1.getRoundScore());
        assertEquals(leaderboard.getPlayers().get(0).getStreak(), player1.getStreak());
        assertEquals(leaderboard.getPlayers().get(0).getPlayerPosition(), 1);

        assertEquals(leaderboard.getPlayers().get(1).getPlayerId(), player2.getId());
        assertEquals(leaderboard.getPlayers().get(1).getPlayerName(), player2.getPlayerName());
        assertEquals(leaderboard.getPlayers().get(1).getRoundScore(), player2.getRoundScore());
        assertEquals(leaderboard.getPlayers().get(1).getStreak(), player2.getStreak());
        assertEquals(leaderboard.getPlayers().get(1).getPlayerPosition(), 2);

    }

    @Test
    public void sortPlayerTest() {
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        testGame = new Game(spotifyService, SongPool.SWITZERLAND, raveWaverRepository);


        ArrayList<Player> players = new ArrayList<>();

        Collections.addAll(players, player1, player2, player3);

        player1.setTotalScore(500);
        player2.setTotalScore(600);
        player3.setTotalScore(300);

        List<Player> actual = testGame.sortPlayers(players);
        List<Player> expected = Arrays.asList(player2, player1, player3);
        assertEquals(actual, expected);

    }

    @Test
    public void generateAvatarTest() throws IOException, ParseException, SpotifyWebApiException {
        List<Player> players = new ArrayList<>();
        testPlayer.setRaveWaverId(0L);
        players.add(testPlayer);
        testGame.generateAvatar(players);

        assertEquals( "https://api.multiavatar.com/Test.png", testPlayer.getProfilePicture());
    }

    @Test
    public void generateAvatarSpaceTest() throws IOException, ParseException, SpotifyWebApiException {
        List<Player> players = new ArrayList<>();
        testPlayer.setPlayerName("name with space");
        testPlayer.setRaveWaverId(0L);
        players.add(testPlayer);
        testGame.generateAvatar(players);

        assertEquals( "https://api.multiavatar.com/dontknow.png", testPlayer.getProfilePicture());
    }

    @Test
    public void generateAvatarRWTest() throws IOException, ParseException, SpotifyWebApiException {
        Game game = new Game(spotifyService, SongPool.SWITZERLAND, raveWaverRepository2);
        List<Player> players = new ArrayList<>();
        testPlayer.setPlayerName("name with space");
        testPlayer.setRaveWaverId(1L);
        players.add(testPlayer);
        RaveWaver raveWaver = new RaveWaver();
        raveWaver.setId(1L);
        raveWaver.setProfilePicture("profilePicture");
        raveWaver.setToken("token");


        Mockito.when(raveWaverRepository2.findById(1L)).thenReturn(Optional.of(raveWaver));
        game.generateAvatar(players);
    }

    @Test
    public void howManyAnsweredTest() {
        assertEquals(0, testGame.howManyAnswered());
    }


    @Test
    public void startGameTest() {
        Game game = new Game(spotifyService, SongPool.SWITZERLAND, raveWaverRepository2);
        ArrayList<Song> songs = new ArrayList<>();

        Mockito.when(spotifyService.getPlaylistsItems("37i9dQZEVXbJiyhoAPEfMK")).thenReturn(songs);

        List<Player> players = new ArrayList<>();
        testPlayer.setRaveWaverId(1L);
        players.add(testPlayer);

        RaveWaver raveWaver = new RaveWaver();
        raveWaver.setId(1L);
        raveWaver.setSpotifyToken("spotifyToken");
        raveWaver.setToken("token");
        raveWaver.setProfilePicture("profilePicture");
        raveWaver.setPassword("password");

        Mockito.when(raveWaverRepository2.findById(1L)).thenReturn(Optional.of(raveWaver));
        Mockito.doNothing().when(spotifyService).authorizationCodeRefresh(raveWaver);
        game.startGame(players);

        Exception e = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            testGame.startNextTurn(players);
        });
        String msg = e.getMessage();
        assertEquals("Index 0 out of bounds for length 0", msg);
    }

    @Test
    public void startRockGameTest() throws Exception {

        SpotifyFetchHelper spotifyFetchHelper = new SpotifyFetchHelper();
        Game game = new Game(spotifyService, SongPool.PARTY, raveWaverRepository2);

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setRoundDuration(RoundDuration.ELEVEN);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.NINE);
        gameSettingsDTO.setSongPool(SongPool.ROCK);
        gameSettingsDTO.setGameRounds(1);
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);

        game.updateGameSettings(gameSettingsDTO);

        spotifyServiceNotMocked = new SpotifyService(raveWaverRepository2);
        ArrayList<Song> songs = spotifyServiceNotMocked.playlistTrackToTrackList(spotifyFetchHelper.getPlaylistFixtures());

        List<Player> players = new ArrayList<>();
        testPlayer.setRaveWaverId(1L);
        players.add(testPlayer);

        RaveWaver raveWaver = new RaveWaver();
        raveWaver.setId(1L);
        raveWaver.setSpotifyToken("spotifyToken");
        raveWaver.setToken("token");
        raveWaver.setProfilePicture("profilePicture");
        raveWaver.setPassword("password");

        Mockito.when(spotifyService.getPlaylistsItems("37i9dQZF1DX4vth7idTQch")).thenReturn(songs);
        Mockito.when(raveWaverRepository2.findById(1L)).thenReturn(Optional.of(raveWaver));
        Mockito.doNothing().when(spotifyService).authorizationCodeRefresh(raveWaver);


        game.startGame(players);
        Question question = game.startNextTurn(players);

        assertEquals("Guess the song artist", question.getQuestion());
    }

    @Test
    public void endRoundTest() throws Exception {

        SpotifyFetchHelper spotifyFetchHelper = new SpotifyFetchHelper();
        Game game = new Game(spotifyService, SongPool.PARTY, raveWaverRepository2);

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setRoundDuration(RoundDuration.ELEVEN);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.NINE);
        gameSettingsDTO.setSongPool(SongPool.ROCK);
        gameSettingsDTO.setGameRounds(1);
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);

        game.updateGameSettings(gameSettingsDTO);

        spotifyServiceNotMocked = new SpotifyService(raveWaverRepository2);
        ArrayList<Song> songs = spotifyServiceNotMocked.playlistTrackToTrackList(spotifyFetchHelper.getPlaylistFixtures());

        List<Player> players = new ArrayList<>();
        testPlayer.setRaveWaverId(1L);
        players.add(testPlayer);

        RaveWaver raveWaver = new RaveWaver();
        raveWaver.setId(1L);
        raveWaver.setSpotifyToken("spotifyToken");
        raveWaver.setToken("token");
        raveWaver.setProfilePicture("profilePicture");
        raveWaver.setPassword("password");

        Mockito.when(spotifyService.getPlaylistsItems("37i9dQZF1DX4vth7idTQch")).thenReturn(songs);
        Mockito.when(raveWaverRepository2.findById(1L)).thenReturn(Optional.of(raveWaver));
        Mockito.doNothing().when(spotifyService).authorizationCodeRefresh(raveWaver);


        game.startGame(players);
        Question question = game.startNextTurn(players);
        LeaderboardDTO leaderboardDTO = game.endRound(players);

        assertEquals(question.getArtist(), leaderboardDTO.getArtist());
    }

    @Test
    public void startUserTopTracksGameTest() throws Exception {

        SpotifyFetchHelper spotifyFetchHelper = new SpotifyFetchHelper();
        Game game = new Game(spotifyService, SongPool.PARTY, raveWaverRepository2);

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setRoundDuration(RoundDuration.FOUR);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.SIX);
        gameSettingsDTO.setSongPool(SongPool.USERSTOPTRACKS);
        gameSettingsDTO.setGameRounds(1);
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);

        game.updateGameSettings(gameSettingsDTO);

        spotifyServiceNotMocked = new SpotifyService(raveWaverRepository2);
        ArrayList<Song> songs = spotifyServiceNotMocked.playlistTrackToTrackList(spotifyFetchHelper.getPlaylistFixtures());

        List<Player> players = new ArrayList<>();
        testPlayer.setRaveWaverId(1L);
        players.add(testPlayer);

        RaveWaver raveWaver = new RaveWaver();
        raveWaver.setId(1L);
        raveWaver.setSpotifyToken("spotifyToken");
        raveWaver.setToken("token");
        raveWaver.setProfilePicture("profilePicture");
        raveWaver.setPassword("password");

        Mockito.when(spotifyService.getPersonalizedPlaylistsItems(1L)).thenReturn(songs);
        Mockito.when(raveWaverRepository2.findById(1L)).thenReturn(Optional.of(raveWaver));
        Mockito.doNothing().when(spotifyService).authorizationCodeRefresh(raveWaver);


        game.startGame(players);
        Question question = game.startNextTurn(players);

        assertEquals("Guess the song artist", question.getQuestion());
    }


    @Test
    public void startUserSavedTracksGameTest() throws Exception {

        SpotifyFetchHelper spotifyFetchHelper = new SpotifyFetchHelper();
        Game game = new Game(spotifyService, SongPool.PARTY, raveWaverRepository2);

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setRoundDuration(RoundDuration.ELEVEN);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.NINE);
        gameSettingsDTO.setSongPool(SongPool.USERSSAVEDTRACKS);
        gameSettingsDTO.setGameRounds(1);
        gameSettingsDTO.setGameMode(GameMode.SONGTITLEGAME);

        game.updateGameSettings(gameSettingsDTO);

        spotifyServiceNotMocked = new SpotifyService(raveWaverRepository2);
        ArrayList<Song> songs = spotifyServiceNotMocked.playlistTrackToTrackList(spotifyFetchHelper.getPlaylistFixtures());

        List<Player> players = new ArrayList<>();
        testPlayer.setRaveWaverId(1L);
        players.add(testPlayer);

        RaveWaver raveWaver = new RaveWaver();
        raveWaver.setId(1L);
        raveWaver.setSpotifyToken("spotifyToken");
        raveWaver.setToken("token");
        raveWaver.setProfilePicture("profilePicture");
        raveWaver.setPassword("password");

        Mockito.when(spotifyService.getSavedTrackItems(1L)).thenReturn(songs);
        Mockito.when(raveWaverRepository2.findById(1L)).thenReturn(Optional.of(raveWaver));
        Mockito.doNothing().when(spotifyService).authorizationCodeRefresh(raveWaver);


        game.startGame(players);
        Question question = game.startNextTurn(players);

        assertEquals("Guess the song title", question.getQuestion());
    }

}
