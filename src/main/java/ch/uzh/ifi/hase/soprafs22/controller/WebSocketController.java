package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.WebSocketService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    Logger log = LoggerFactory.getLogger(WebSocketController.class);
    private SimpMessageSendingOperations messTemplate;
    private GameService gameService;
    private WebSocketService webSocketService;

    public WebSocketController(GameService gameService, WebSocketService webSocketService) {
        this.gameService = gameService;
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/lobbies/{lobbyId}/setup")
    public void updateGameSettings(@DestinationVariable int lobbyId, GameSettingsDTO gameSettingsDTO) {
        // GameSettingsDTO gameSettingsDTO
        log.info("Lobby" + lobbyId + ": Game settings updated");
        gameService.updateGameSettings(gameSettingsDTO, lobbyId);
        String destination = "/topic/lobbies/" + lobbyId;
        this.webSocketService.sendMessageToClients(destination, gameSettingsDTO);
    }

    @MessageMapping("/lobbies/{lobbyId}/start-game")
    public void startGame(@DestinationVariable int lobbyId) {
        log.info("Lobby" + lobbyId + ": Game started.");
        gameService.startGame(lobbyId);
        QuestionDTO questionToSend = gameService.startNextRound(lobbyId);
        String destination = "/topic/lobbies/" + lobbyId;
        this.webSocketService.sendMessageToClients(destination, questionToSend);
    }

    @MessageMapping("/lobbies/{lobbyId}/player/{playerId}/save-answer")
    public void saveAnswer(@DestinationVariable int lobbyId, @DestinationVariable int playerId, Answer answer) {
        log.info("Lobby" + lobbyId + ": Player" + playerId + "has answered.");
        gameService.saveAnswer(answer, playerId);
    }

    @MessageMapping("/lobbies/{lobbyId}/end-round")
    public void endRound(@DestinationVariable Long lobbyId) {
        log.info("Lobby" + lobbyId + ": round is over");
        // GameService rüeft evaluator uf
        LeaderboardDTO leaderboard = gameService.endRound(lobbyId);
        String destination = "/topic/lobbies/" + lobbyId;
        this.webSocketService.sendMessageToClients(destination, leaderboard);
    }

    @MessageMapping("/lobbies/{lobbyId}/next-round")
    public void startNextRound(@DestinationVariable int lobbyId) {
        log.info("Next round started");
        QuestionDTO questionToSend = gameService.startNextRound(lobbyId);
        String destination = "/topic/lobbies/" + lobbyId;
        this.webSocketService.sendMessageToClients(destination, questionToSend);
    }

    @MessageMapping("/lobbies/{lobbyId}/end-game")
    public void endGame(@DestinationVariable int lobbyId) {
        log.info("Lobby" + lobbyId + ": Game ended");
    }

}
