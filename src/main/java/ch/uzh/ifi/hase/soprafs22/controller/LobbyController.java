package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.FourRaveWaversConnectedDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyIdDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LobbyController {
    private final GameService gameService;
    private final SpotifyService spotifyService;
    private final PlayerService playerService;
    private final RaveWaverService raveWaverService;
    private final RaveWaverRepository raveWaverRepository;
    Logger log = LoggerFactory.getLogger(LobbyController.class);

    LobbyController(GameService gameService, SpotifyService spotifyService, PlayerService playerService,
                    RaveWaverService raveWaverService, RaveWaverRepository raveWaverRepository) {
        this.gameService = gameService;
        this.spotifyService = spotifyService;
        this.playerService = playerService;
        this.raveWaverService = raveWaverService;
        this.raveWaverRepository = raveWaverRepository;
    }

    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyIdDTO createNewLobby() {
        LobbyIdDTO lobbyIdDTO = new LobbyIdDTO();
        lobbyIdDTO.setLobbyId(gameService.createNewLobby(spotifyService));
        log.info("Lobby" + lobbyIdDTO.getLobbyId() + ": created");
        return lobbyIdDTO;

    }

    @PostMapping("/lobbies/{lobbyId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PlayerGetDTO createPlayer(@RequestBody PlayerPostDTO playerPostDTO, @PathVariable Long lobbyId,
                                     HttpServletResponse response, HttpServletRequest token) {

        if (raveWaverRepository.findByToken(token.getHeader("Authorization")) != null) {
            Player newPlayer = raveWaverService.addRaveWaverToLobby(token, lobbyId);
            log.info("RaveWaver as a Player " + newPlayer.getPlayerName() + " with ID: " + newPlayer.getId()
                    + " created");
            response.addHeader("Authorization", newPlayer.getToken());
            playerService.greetPlayers(newPlayer);

            return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(newPlayer);
        }

        Player playerToAdd = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);
        playerToAdd.setLobbyId(lobbyId);

        Player newPlayer = playerService.addPlayer(playerToAdd);

        response.addHeader("Authorization", newPlayer.getToken());

        log.info("Player " + newPlayer.getPlayerName() + " with ID: " + newPlayer.getId() + " created");
        playerService.greetPlayers(newPlayer);


        return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(newPlayer);

    }

    @GetMapping("/lobbies/{lobbyId}/likedSongsUnlocked")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FourRaveWaversConnectedDTO checkFourRaveWaversConnected(@PathVariable Long lobbyId) {

        log.info("Lobby" + lobbyId + ": checking if four players are connected to Spotify");
        FourRaveWaversConnectedDTO fourRaveWaversConnectedDTO = new FourRaveWaversConnectedDTO();
        fourRaveWaversConnectedDTO.setFourRaveWaversConnected(playerService.checkFourRaveWaversConnected(lobbyId));

        return fourRaveWaversConnectedDTO;
    }
}
