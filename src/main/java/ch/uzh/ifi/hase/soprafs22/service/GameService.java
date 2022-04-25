package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.AnswerDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.EndGameDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
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

    private int lobbyToCreate;

    private final PlayerRepository playerRepository;
    //private final PlayerService playerService;
    //private Game game;


    @Autowired
    public GameService(@Qualifier("PlayerRepository") PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.lobbyToCreate = 0;
    }

    //TODO lobbyId is hardcoded so far
    public int createNewLobby(SpotifyService spotifyService){
        lobbyToCreate++;
        Game newGame = new Game(spotifyService, SongPool.SWITZERLAND);
        GameRepository.addGame(lobbyToCreate, newGame);
        return lobbyToCreate;
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

    public void endGame(){
    }

    public void updateGameSettings(GameSettingsDTO gameSettingsDTO, int lobbyId){
        Game game = GameRepository.findByLobbyId(lobbyId);

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

    public void endRound(Long lobbyId){
        playerRepository.findByLobbyId(lobbyId);
    }

}
