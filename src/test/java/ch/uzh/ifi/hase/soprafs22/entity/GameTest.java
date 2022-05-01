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

import java.util.Arrays;
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
}
