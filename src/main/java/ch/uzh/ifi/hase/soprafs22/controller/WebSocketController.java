package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.WebSocketService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.AnswerDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.EndGameDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.LeaderboardDTO;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    Logger log = LoggerFactory.getLogger(WebSocketController.class);

    private GameService gameService;

    public WebSocketController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/lobby/{lobbyId}/setup")
    public void updateGameSettings(@DestinationVariable int lobbyId, GameSettingsDTO gameSettingsDTO){
        log.info("Lobby" + lobbyId + ": Game settings updated");
        gameService.updateGameSettings(gameSettingsDTO);
    }

    @MessageMapping("/lobby/{lobbyId}/start-game")
    public void startGame(@DestinationVariable int lobbyId){
        log.info("Lobby" + lobbyId + ": Game started.");
        gameService.startGame(lobbyId);
    }

    @MessageMapping("/lobby/{lobbyId}/player/{playerId}/save-answer")
    public void saveAnswer(@DestinationVariable int lobbyId, @DestinationVariable int playerId, AnswerDTO answerDTO){
        log.info("Lobby" + lobbyId + ": Player" + playerId + "has answered.");
        gameService.saveAnswer(answerDTO, playerId);
    }

    @MessageMapping("/lobby/{lobbyId}/end-round")
    public void endRound(@DestinationVariable int lobbyId, @DestinationVariable int playerId, AnswerDTO answerDTO){
        log.info("Lobby" + lobbyId + ": Player" + playerId + "has answered.");
        //GameService rüeft evaluator uf
    }

    @MessageMapping("/lobby/{lobbyId}/end-game")
    public void endGame(@DestinationVariable int lobbyId, EndGameDTO endGameDTO){
        log.info("Lobby" + lobbyId + ": Game ended");
        gameService.endGame(endGameDTO);
    }

    @MessageMapping("/lobby/next-round")
    public void startNextRound(@DestinationVariable int lobbyId){
        log.info("Lobby" + lobbyId + ": Next round started");
        gameService.startNextRound(lobbyId);
    }

}
