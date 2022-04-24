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
        // GameSettingsDTO gameSettingsDTO
        log.info("Lobby" + lobbyId + ": Game settings updated");
        gameService.updateGameSettings(gameSettingsDTO);
        String destination = "/topic/lobby/"+lobbyId.toString();
        this.webSocketService.sendMessageToClients(destination, gameSettingsDTO);
    }

    @MessageMapping("/lobby/{lobbyId}/leaderboard")
    public void updateLeaderboard(@DestinationVariable Integer lobbyId, LeaderboardDTO leaderboardDTO){
        log.info("Lobby" + lobbyId + ": Leaderboard updated");
        gameService.updateLeaderboard(leaderboardDTO);
    }

    //TODO: @DestinationVariable Integer lobbyId
    @MessageMapping("/lobby/next-round")
    public void startNextRound(@DestinationVariable Integer lobbyId){
        log.info("Next round started");
        QuestionDTO questionToSend = DTOMapper.INSTANCE.convertEntityToQuestionDTO(gameService.startNextRound(1));
        this.webSocketService.sendMessageToClients("/topic/lobby/{lobbyId}", questionToSend);
    }
    //value = "/PlaylistItems", produces = MediaType.TEXT_PLAIN_VALUE
    //@SendTo("/topic/testing")
    @MessageMapping("/test")
    public void test(SimpMessageHeaderAccessor simpHeader){
        String id = IdentityHeader.getIdentity(simpHeader);
        log.info("Player " + id + ": Connection established");
        SpotifyPostDTO test = new SpotifyPostDTO();
        test.setCode("Das isch d antwort vo üsem server");

        this.webSocketService.sendMessageToClients(id, test);
        //return(test);

    }
}
