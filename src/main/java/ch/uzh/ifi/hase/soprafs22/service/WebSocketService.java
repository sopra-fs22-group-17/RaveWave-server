package ch.uzh.ifi.hase.soprafs22.service;


import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WebSocketService {

    protected final PlayerRepository playerRepo;
    private final PlayerService playerService;
    @Autowired // Automatic injection of beans
    protected SimpMessagingTemplate smesg;
    Logger log = LoggerFactory.getLogger(WebSocketService.class);

    public WebSocketService(@Qualifier("PlayerRepository") PlayerRepository playerRepository,
                            @Lazy PlayerService playerService) {
        this.playerRepo = playerRepository;
        this.playerService = playerService;
    }

    protected void convertAndSendToPlayer(Long id, String path, Object dto) {
        String stringId = Long.toString(id);
        this.smesg.convertAndSendToUser(stringId, path, dto);
    }

    protected void convertAndSendToAllInLobby(String path, String dest, Object dto, long lobbyId) {
        List<Player> lobby = this.playerRepo.findByLobbyId(lobbyId);
        for (Player player : lobby) {
            convertAndSendToPlayer(player.getId(), path + lobbyId + dest, dto);
        }
    }

    public void sendMessageToClients(String destination, Object dto) {
        this.smesg.convertAndSend(destination, dto);
        //this.smesg.convertAndSendToUser(id, "/topic/testing", dto);

    }

}
