package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
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

import java.util.Optional;

/**
 * GameService
 */
@Service
@Transactional
public class GameService {
    Logger log = LoggerFactory.getLogger(GameService.class);

    private final PlayerRepository playerRepository;
    //private final PlayerService playerService;
    //private Game game;


    @Autowired
    public GameService(@Qualifier("PlayerRepository") PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    //TODO lobbyId is hardcoded so far
    public void createNewLobby(SpotifyService spotifyService){
        Game newGame = new Game(spotifyService, SongPool.SWITZERLAND);
        GameRepository.addGame("1", newGame);
    }

    public void startGame(int lobbyId){
        GameRepository.findByLobbyId(lobbyId).startGame();
    }

    public void saveAnswer(AnswerDTO answerDTO, int playerId){
        Answer answer = answerDTO.getAnswer();
        Player player = playerRepository.findById((long)playerId);
        player.saveAnswer(answer);
        //save received answer to the corresponding player
    }

    public void endGame(EndGameDTO endGameDTO){
    }

    public void updateGameSettings(GameSettingsDTO gameSettingsDTO){
        Game game = GameRepository.findByLobbyId(gameSettingsDTO.getLobbyID());

        //update game settings
        game.updateGameSettings(gameSettingsDTO);
        // GameRepository.findByLobbyId("1").
    }

    public void updateLeaderboard(LeaderboardDTO leaderboardDTO){
        System.out.println("We did it");
    }

    public Question startNextRound(int lobbyId){
        return GameRepository.findByLobbyId(lobbyId).startNextTurn();
    }

}
