package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.WebSocketService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.AnswerDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.EndGameDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.LeaderboardDTO;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    Logger log = LoggerFactory.getLogger(WebSocketController.class);

    private GameService gameService;

    public WebSocketController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/lobby/{lobbyId}/start-game")
    public void startGame(@DestinationVariable Integer lobbyId){
        log.info("Lobby" + lobbyId + ": Game started.");
        gameService.startGame(lobbyId);
    }

    @MessageMapping("/lobby/{lobbyId}/player/{playerId}/check-answer")
    public void checkAnswer(@DestinationVariable Integer lobbyId, @DestinationVariable Integer playerId, AnswerDTO answerDTO){
        log.info("Lobby" + lobbyId + ": Player" + playerId + "has answered.");
        gameService.saveAnswer(answerDTO);
    }

    @MessageMapping("/lobby/{lobbyId}/end-game")
    public void endGame(@DestinationVariable Integer lobbyId, EndGameDTO endGameDTO){
        log.info("Lobby" + lobbyId + ": Game ended");
        gameService.endGame(endGameDTO);
    }

    @MessageMapping("/lobby/{lobbyId}/setup")
    public void updateGameSettings(@DestinationVariable Integer lobbyId, GameSettingsDTO gameSettingsDTO){
        log.info("Lobby" + lobbyId + ": Game settings updated");
        gameService.updateGameSettings(gameSettingsDTO);
    }

    @MessageMapping("/lobby/{lobbyId}/leaderboard")
    public void updateLeaderboard(@DestinationVariable Integer lobbyId, LeaderboardDTO leaderboardDTO){
        log.info("Lobby" + lobbyId + ": Leaderboard updated");
        gameService.updateLeaderboard(leaderboardDTO);
    }

    @MessageMapping("/lobby/{lobbyId}/next-round")
    public void startNextRound(@DestinationVariable Integer lobbyId){
        log.info("Lobby" + lobbyId + ": Next round started");
        gameService.startNextRound(lobbyId);
    }
}
