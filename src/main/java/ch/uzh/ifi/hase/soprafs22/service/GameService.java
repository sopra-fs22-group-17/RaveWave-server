package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.AnswerOptions;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * GameService
 */
@Service
@Transactional
public class GameService {
    private final PlayerRepository playerRepository;
    Logger log = LoggerFactory.getLogger(GameService.class);
    private int lobbyToCreate;

    @Autowired
    public GameService(@Qualifier("PlayerRepository") PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.lobbyToCreate = 0;
    }

    public int createNewLobby(SpotifyService spotifyService) {
        lobbyToCreate++;
        Game newGame = new Game(spotifyService, SongPool.SWITZERLAND);
        GameRepository.addGame(lobbyToCreate, newGame);
        return lobbyToCreate;
    }

    public void startGame(int lobbyId) {
        List<Player> players = playerRepository.findByLobbyId((long) lobbyId);

        GameRepository.findByLobbyId(lobbyId).startGame(players);
    }

    public void saveAnswer(Answer answer, int playerId) {
        Player player = playerRepository.findById(playerId);
        Game game = GameRepository.findByLobbyId((int) player.getlobbyId());
        answer.setPlayerId((long) playerId);
        game.addAnswers(answer);
        // save received answer to the corresponding player
    }

    public void updateGameSettings(GameSettingsDTO gameSettingsDTO, int lobbyId) {
        Game game = GameRepository.findByLobbyId(lobbyId);

        // update game settings
        game.updateGameSettings(gameSettingsDTO);
        // GameRepository.findByLobbyId("1").
    }

    public QuestionDTO startNextRound(int lobbyId) {
        Question nextQuestion = GameRepository.findByLobbyId(lobbyId).startNextTurn();
        QuestionDTO nextQuestionDTO = new QuestionDTO();

        nextQuestionDTO.setQuestion(nextQuestion.getQuestion());
        nextQuestionDTO.setPreviewURL(nextQuestion.getPreviewUrl());
        nextQuestionDTO.setSongTitle(nextQuestion.getSongTitle());
        nextQuestionDTO.setPlayBackDuration(nextQuestion.getPlaybackDuration().toString());

        ArrayList<AnswerOptions> options = new ArrayList<>();
        List<String> singleAnswer = nextQuestion.getAnswers();

        int i = 1;
        for (String answer : singleAnswer) {
            AnswerOptions option = new AnswerOptions();
            option.setAnswer(answer);
            option.setAnswerId(i);
            options.add(option);
            option.setAlbumPicture(nextQuestion.getAlbumCovers().get(i - 1));
            i++;
        }
        nextQuestionDTO.setAnswers(options);

        return nextQuestionDTO;
    }

    public LeaderboardDTO endRound(long lobbyId) {
        Game game = GameRepository.findByLobbyId((int) lobbyId);
        List<Player> players = playerRepository.findByLobbyId(lobbyId);
        LeaderboardDTO leaderboardDTO = game.endRound(players);
        if (leaderboardDTO.isGameOver()) {
            endGame(lobbyId);
        }
        return leaderboardDTO;
    }

    private void endGame(long lobbyId) {
        playerRepository.deleteByLobbyId(lobbyId);
        GameRepository.removeGame((int) lobbyId);
    }

}
