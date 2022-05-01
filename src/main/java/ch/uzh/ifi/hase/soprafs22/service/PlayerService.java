package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
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

    @Autowired
    public PlayerService(@Qualifier("PlayerRepository") PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // returns playerToken
    public Player addPlayer(Player newPlayer) {
        Long lobbyId = newPlayer.getlobbyId();

        if (GameRepository.findByLobbyId(lobbyId.intValue()).hasStarted()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The game has already started, you cannot add a user to this lobby!");
        }
        checkIfPlayerExists(newPlayer);
        checkIfLobbyForPlayerExists(newPlayer);
        newPlayer.setToken(UUID.randomUUID().toString());
        newPlayer.addToScore(0);
        newPlayer.setStreak(0);

        newPlayer = playerRepository.save(newPlayer);
        playerRepository.flush();

        return newPlayer;
    }

    private void checkIfPlayerExists(Player playerToBeCreated) {
        Player userByUsername = playerRepository.findByPlayerNameAndLobbyId(playerToBeCreated.getPlayerName(),
                playerToBeCreated.getlobbyId());

        if (userByUsername != null && userByUsername.getlobbyId() == playerToBeCreated.getlobbyId()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This username does already exist!");
        }
    }

    private void checkIfLobbyForPlayerExists(Player playerToBeCreated) {
        try {
            GameRepository.findByLobbyId((int) playerToBeCreated.getlobbyId());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adding the player failed: " + e.getMessage());

        }

    }

}
