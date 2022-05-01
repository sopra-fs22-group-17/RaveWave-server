package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.Evaluator;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class GameTest {
    private Game game;

    @Mock
    private SpotifyService spotifyService;


    @BeforeEach
    void setup() {
        game = new Game(spotifyService, SongPool.SWITZERLAND);
    }

    @Test
    public void updateGameSettingsTest() {

        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setGameMode(GameMode.ARTISTGAME);
        gameSettingsDTO.setGameRounds(10);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.EIGHTEEN);
        gameSettingsDTO.setSongPool(SongPool.COUNTRY);

        game.updateGameSettings(gameSettingsDTO);

        GameSettingsDTO actual = game.getGameSettings();

        assertEquals(gameSettingsDTO.getGameRounds(), actual.getGameRounds());
        assertEquals(gameSettingsDTO.getGameMode(), actual.getGameMode());
        assertEquals(gameSettingsDTO.getPlayBackDuration(), actual.getPlayBackDuration());
        assertEquals(gameSettingsDTO.getRoundDuration(), actual.getRoundDuration());
        assertEquals(gameSettingsDTO.getSongPool(), actual.getSongPool());
    }

    @Test
    public void fillLeaderboardTest(){
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

        LeaderboardDTO leaderboard = game.fillLeaderboard(players);

        // given().willReturn(1);


        assertEquals(leaderboard.getPlayers().get(0).getPlayerId(), player1.getId());


    }

    @Test
    public void sortPlayerTest(){
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        game = new Game(spotifyService, SongPool.SWITZERLAND);

        ArrayList<Player> players = new ArrayList<>();

        Collections.addAll(players, player1, player2, player3);


        player1.setTotalScore(500);
        player2.setTotalScore(600);
        player3.setTotalScore(300);

        List<Player> actual = game.sortPlayers(players);
        List<Player> expected = Arrays.asList(player2, player1, player3);
        assertEquals(actual, expected);

    }
}