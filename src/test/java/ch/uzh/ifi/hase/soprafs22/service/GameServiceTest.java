package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.CurrentAnswersDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.QuestionDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GameServiceTest {
    @Mock
    GameRepository gameRepository;
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;
    @InjectMocks
    private GameService gameService;
    @Mock
    private Game testGame;
    @MockBean
    private SpotifyService spotifyService;
    @Mock
    private Player testPlayer;
    @MockBean
    private RaveWaverRepository raveWaverRepository;
    @Mock
    private Answer testAnswer;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        testPlayer = new Player();
        testPlayer.setId(1L);
        testPlayer.setLobbyId(1L);
        testPlayer.setPlayerName("Test");
        testPlayer.setToken("token");
        testGame = new Game(spotifyService, SongPool.SWITZERLAND, raveWaverRepository);
        testAnswer = new Answer();
        testAnswer.setToken("token");
        gameService.createNewLobby(spotifyService);
        playerService.addPlayer(testPlayer);
        GameRepository.addGame(1, testGame);


        Mockito.when(playerRepository.findById(testPlayer.getlobbyId())).thenReturn(testPlayer);
        Mockito.when(playerRepository.findByToken(testAnswer.getToken())).thenReturn(testPlayer);
        Mockito.when(playerRepository.save(Mockito.any())).thenReturn(testPlayer);

    }

    @AfterEach
    void teardown() {
        GameRepository.removeGame(1);
    }


    @Test
    void saveAnswerTest() {

        gameService.saveAnswer(testAnswer, 1);
        assertEquals(testAnswer, testGame.getListOfAnswers().get(0));
    }

    @Test
    void saveAnswerWithoutTokenTest() {

        Answer answerWithoutToken = new Answer();

        assertThrows(ResponseStatusException.class, () -> gameService.saveAnswer(answerWithoutToken, 1));
    }

    @Test
    void saveAnswerWrongTokenTest() {
        Player testPlayer2 = new Player();
        testPlayer2.setToken("WrongToken");
        testPlayer2.setLobbyId(1L);
        testPlayer2.setPlayerName("Test2");
        testPlayer2.setId(2L);
        playerService.addPlayer(testPlayer2);
        Answer answerWrongToken = new Answer();
        answerWrongToken.setToken("WrongToken");

        assertThrows(ResponseStatusException.class, () -> gameService.saveAnswer(answerWrongToken, 1));
    }

    @Test
    void updateGameSettingsTest() {
        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setRoundDuration(RoundDuration.EIGHTEEN);
        gameSettingsDTO.setGameRounds(12);
        gameSettingsDTO.setGameMode(GameMode.SONGTITLEGAME);
        gameSettingsDTO.setSongPool(SongPool.LATIN);
        gameSettingsDTO.setPlayBackDuration(PlaybackDuration.EIGHT);

        gameService.updateGameSettings(gameSettingsDTO, 1);

        assertEquals(gameSettingsDTO.getGameMode(), testGame.getGameSettings().getGameMode());
    }
    //mockito cant mock final or static classes


    @Test
    void startNextRoundTest() {
        Player testPlayer2 = new Player();
        testPlayer2.setId(2L);
        testPlayer2.setLobbyId(2L);
        testPlayer2.setPlayerName("testy");
        List<Player> players = new ArrayList<>();
        players.add(testPlayer2);

        ArrayList<String> answers = new ArrayList<>();
        answers.add("testAnswer");
        ArrayList<String> pictures = new ArrayList<>();
        pictures.add("picture");

        Question question = new Question();
        question.setQuestion("question");
        question.setPreviewUrl("songId");
        question.setSongTitle("songTitle");
        question.setPlaybackDuration(PlaybackDuration.EIGHTEEN);
        question.setCurrentRound(2);
        question.setTotalRounds(10);
        question.setAnswers(answers);
        question.setPictures(pictures);

        Game game = org.mockito.Mockito.mock(Game.class);
        gameService.createNewLobby(spotifyService);
        GameRepository.addGame(2, game);
        playerService.addPlayer(testPlayer2);
        Mockito.when(playerRepository.findByLobbyId(2L)).thenReturn(players);
        Mockito.when(game.startNextTurn(players)).thenReturn(question);


        QuestionDTO questionDTO = gameService.startNextRound(2);

        assertEquals(questionDTO.getQuestion(), question.getQuestion());
    }

    @Test
    void fillAnswersTest() {
        Game game = org.mockito.Mockito.mock(Game.class);

        GameRepository.addGame(2, game);
        Mockito.when(game.howManyAnswered()).thenReturn(2);
        Mockito.when(game.getNumberOfPlayers()).thenReturn(10);

        CurrentAnswersDTO currentAnswersDTO = gameService.fillAnswers(2);
        assertEquals(currentAnswersDTO.getCurrentAnswers(), 2);
        assertEquals(currentAnswersDTO.getExpectedAnswers(), 10);
    }


    @Test
    void endRoundTest() {
        Player testPlayer2 = new Player();
        testPlayer2.setId(2L);
        testPlayer2.setLobbyId(2L);
        testPlayer2.setPlayerName("testy");
        List<Player> players = new ArrayList<>();
        players.add(testPlayer2);
        Game game = org.mockito.Mockito.mock(Game.class);
        gameService.createNewLobby(spotifyService);
        GameRepository.addGame(2, game);
        playerService.addPlayer(testPlayer2);

        LeaderboardDTO leaderboardDTO = new LeaderboardDTO();
        leaderboardDTO.setCorrectAnswer("correctAnswer");
        leaderboardDTO.setGameOver(false);
        leaderboardDTO.setArtist("artist");
        leaderboardDTO.setSpotifyLink("spotifyLink");
        leaderboardDTO.setSongTitle("songTitle");
        leaderboardDTO.setCoverUrl("coverUrl");
        //leaderboardDTO.setPlayers();

        Mockito.when(playerRepository.findByLobbyId(2L)).thenReturn(players);
        Mockito.when(game.endRound(players)).thenReturn(leaderboardDTO);
        LeaderboardDTO actual = gameService.endRound(2);
        assertEquals("artist", actual.getArtist());
    }


    @Test
    void endRoundGameOverTest() {
        LeaderboardDTO leaderboardDTO = new LeaderboardDTO();
        leaderboardDTO.setCorrectAnswer("correctAnswer");
        leaderboardDTO.setGameOver(true);
        leaderboardDTO.setArtist("artist");
        leaderboardDTO.setSpotifyLink("spotifyLink");
        leaderboardDTO.setSongTitle("songTitle");
        leaderboardDTO.setCoverUrl("coverUrl");

        Player testPlayer2 = new Player();
        testPlayer2.setId(2L);
        testPlayer2.setLobbyId(2L);
        testPlayer2.setPlayerName("testy");
        List<Player> players = new ArrayList<>();
        players.add(testPlayer2);
        Game game = org.mockito.Mockito.mock(Game.class);
        gameService.createNewLobby(spotifyService);
        GameRepository.addGame(2, game);
        playerService.addPlayer(testPlayer2);

        Mockito.when(playerRepository.findByLobbyId(2L)).thenReturn(players);
        Mockito.when(game.endRound(players)).thenReturn(leaderboardDTO);
        LeaderboardDTO actual = gameService.endRound(2);

        assertThrows(ResponseStatusException.class, () -> GameRepository.findByLobbyId(2));
    }


}
