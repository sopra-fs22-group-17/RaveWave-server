package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.AnswerDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.EndGameDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.LeaderboardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;

/**
 * GameService
 */
@Service
@Transactional
public class GameService {
    Logger log = LoggerFactory.getLogger(GameService.class);

    private final PlayerRepository playerRepository;
    private Game game;

    @Autowired
    public GameService(@Qualifier("PlayerRepository") PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void startGame(Integer lobbyId){
        game.startGame(lobbyId);
    }

    public void saveAnswer(AnswerDTO answerDTO){
        //save received answer to the corresponding player
    }

    public void endGame(EndGameDTO endGameDTO){
    }

    public void updateGameSettings(GameSettingsDTO gameSettingsDTO){
    }

    public void updateLeaderboard(LeaderboardDTO leaderboardDTO){
    }

    public void startNextRound(Integer lobbyId){

    }

}
