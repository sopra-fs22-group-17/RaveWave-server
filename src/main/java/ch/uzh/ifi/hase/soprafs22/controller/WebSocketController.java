package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.WebSocketService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.QuestionDTO;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

@Controller
public class WebSocketController {
    private final GameService gameService;
    private final WebSocketService webSocketService;
    private final String destination = "/topic/lobbies/";
    Logger log = LoggerFactory.getLogger(WebSocketController.class);

    public WebSocketController(GameService gameService, WebSocketService webSocketService) {
        this.gameService = gameService;
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/lobbies/{lobbyId}/setup")
    public void updateGameSettings(@DestinationVariable int lobbyId, GameSettingsDTO gameSettingsDTO) {
        // GameSettingsDTO gameSettingsDTO
        log.info("Lobby" + lobbyId + ": Game settings updated");
        gameService.updateGameSettings(gameSettingsDTO, lobbyId);
        this.webSocketService.sendMessageToClients(destination + lobbyId, gameSettingsDTO);
    }

    @MessageMapping("/lobbies/{lobbyId}/start-game")
    public void startGame(@DestinationVariable int lobbyId) throws IOException, ParseException, SpotifyWebApiException {
        log.info("Lobby" + lobbyId + ": Game started.");
        gameService.startGame(lobbyId);
        QuestionDTO questionToSend = gameService.startNextRound(lobbyId);
        this.webSocketService.sendMessageToClients(destination + lobbyId, questionToSend);
    }

    @MessageMapping("/lobbies/{lobbyId}/player/{playerId}/save-answer")
    public void saveAnswer(@DestinationVariable int lobbyId, @DestinationVariable int playerId, Answer answer) {
        log.info("Lobby " + lobbyId + ": Player " + playerId + " has answered.");
        gameService.saveAnswer(answer, playerId);
    }

    @MessageMapping("/lobbies/{lobbyId}/end-round")
    public void endRound(@DestinationVariable Long lobbyId) {
        log.info("Lobby" + lobbyId + ": round is over");
        // GameService rüeft evaluator uf
        LeaderboardDTO leaderboard = gameService.endRound(lobbyId);
        this.webSocketService.sendMessageToClients(destination + lobbyId, leaderboard);
    }

    @MessageMapping("/lobbies/{lobbyId}/next-round")
    public void startNextRound(@DestinationVariable int lobbyId) {
        log.info("Next round started");
        QuestionDTO questionToSend = gameService.startNextRound(lobbyId);
        this.webSocketService.sendMessageToClients(destination + lobbyId, questionToSend);
    }

}
