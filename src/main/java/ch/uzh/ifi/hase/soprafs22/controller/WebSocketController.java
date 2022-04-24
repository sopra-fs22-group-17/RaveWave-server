package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.WebSocketService;
import ch.uzh.ifi.hase.soprafs22.utils.IdentityHeader;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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

    @MessageMapping("/lobbies/{lobbyId}/start-game")
    public void startGame(@DestinationVariable int lobbyId) {
        log.info("Lobby" + lobbyId + ": Game started.");
        gameService.startGame(lobbyId);
    }

    @MessageMapping("/lobbies/{lobbyId}/player/{playerId}/save-answer")
    public void saveAnswer(@DestinationVariable int lobbyId, @DestinationVariable int playerId, AnswerDTO answerDTO) {
        log.info("Lobby" + lobbyId + ": Player" + playerId + "has answered.");
        gameService.saveAnswer(answerDTO, playerId);
    }

    @MessageMapping("/lobbies/{lobbyId}/end-game")
    public void endGame(@DestinationVariable int lobbyId, EndGameDTO endGameDTO) {
        log.info("Lobby" + lobbyId + ": Game ended");
        gameService.endGame(endGameDTO);
    }

    @MessageMapping("/lobbies/{lobbyId}/setup")
    public void updateGameSettings(@DestinationVariable Integer lobbyId, GameSettingsDTO gameSettingsDTO) {
        // GameSettingsDTO gameSettingsDTO
        log.info("Lobby" + lobbyId + ": Game settings updated");
        gameService.updateGameSettings(gameSettingsDTO);
        String destination = "/topic/lobby/" + lobbyId.toString();
        this.webSocketService.sendMessageToClients(destination, gameSettingsDTO);
    }

    @MessageMapping("/lobbies/{lobbyId}/end-round")
    public void endRound(@DestinationVariable int lobbyId, @DestinationVariable int playerId, AnswerDTO answerDTO) {
        log.info("Lobby" + lobbyId + ": Player" + playerId + "has answered.");
        //GameService rüeft evaluator uf
    }

    //TODO: @DestinationVariable Integer lobbyId
    @MessageMapping("/lobbies/{lobbyId}/next-round")
    public void startNextRound(@DestinationVariable int lobbyId) {
        log.info("Next round started");
        QuestionDTO questionToSend = DTOMapper.INSTANCE.convertEntityToQuestionDTO(gameService.startNextRound(1));
        String destination = "/topic/lobbies/" + lobbyId;
        this.webSocketService.sendMessageToClients(destination, questionToSend);
    }
}
