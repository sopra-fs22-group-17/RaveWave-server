package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.PlayerJoinDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * GameService
 */
@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;
    Logger log = LoggerFactory.getLogger(PlayerService.class);
    private final WebSocketService webSocketService;

    @Autowired
    public PlayerService(@Qualifier("PlayerRepository") PlayerRepository playerRepository, WebSocketService webSocketService) {
        this.playerRepository = playerRepository;
        this.webSocketService = webSocketService;
    }

    public static void checkIfLobbyForPlayerExists(Player playerToBeCreated) {
        try {
            GameRepository.findByLobbyId((int) playerToBeCreated.getlobbyId());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adding the player failed: " + e.getMessage());

        }

    }

    // returns playerToken
    public Player addPlayer(Player newPlayer) {
        Long lobbyId = newPlayer.getlobbyId();

        if (GameRepository.findByLobbyId(lobbyId.intValue()).hasStarted()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "The game has already started, you cannot add a user to this lobby!");
        }
        checkIfUsernameValid(newPlayer);
        checkIfLobbyForPlayerExists(newPlayer);
        newPlayer.setToken(UUID.randomUUID().toString());
        newPlayer.addToScore(0);
        newPlayer.setStreak(0);

        newPlayer = playerRepository.save(newPlayer);
        playerRepository.flush();

        return newPlayer;
    }

    private void checkIfUsernameValid(Player playerToBeCreated) {
        Player userByUsername = playerRepository.findByPlayerNameAndLobbyId(playerToBeCreated.getPlayerName(),
                playerToBeCreated.getlobbyId());

        if (userByUsername != null && userByUsername.getlobbyId() == playerToBeCreated.getlobbyId()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This username does already exist!");
        }

        if (playerToBeCreated.getPlayerName().contains("[RW]")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Your username can not contain \"[RW]\"!");
        }
    }

    public void greetPlayers(Player player){
        PlayerJoinDTO playerJoinDTO = new PlayerJoinDTO();
        playerJoinDTO.setName(player.getPlayerName());

        this.webSocketService.sendMessageToClients("/topic/lobbies/" + player.getlobbyId(), playerJoinDTO);
    }

}
