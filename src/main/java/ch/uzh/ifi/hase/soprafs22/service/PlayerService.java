package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * GameService
 */
@Service
@Transactional
public class PlayerService {
    Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(@Qualifier("PlayerRepository") PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // returns playerToken
    public Player addPlayer(Player newPlayer) {
        checkIfPlayerExists(newPlayer);
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("This username does already exist!"));
        }
    }

}
