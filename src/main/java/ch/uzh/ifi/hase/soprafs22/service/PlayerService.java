package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * GameService
 */
@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;
    Logger log = LoggerFactory.getLogger(PlayerService.class);
    private RaveWaverRepository raveWaverRepository;

    @Autowired
    public PlayerService(@Qualifier("PlayerRepository") PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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

    public boolean checkIfFourConnected(Long lobbyId){

        List<Player> players = playerRepository.findByLobbyId(lobbyId);
        System.out.println(players.get(0));
        int counter = 0;
        for (Player player: players){
            if (player.getRaveWaverId() !=0){
                System.out.println(player.getRaveWaverId());
                counter++;
            }
        }
        return counter >= 4;
    }

}
