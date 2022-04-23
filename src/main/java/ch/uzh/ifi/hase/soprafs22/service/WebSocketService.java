package ch.uzh.ifi.hase.soprafs22.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;

@Service
@Transactional
public class WebSocketService {

	Logger log = LoggerFactory.getLogger(WebSocketService.class);

	protected final PlayerRepository playerRepo;
	private final PlayerService playerService;

	public WebSocketService(@Qualifier("PlayerRepository") PlayerRepository playerRepository,
			@Lazy PlayerService playerService) {
		this.playerRepo = playerRepository;
		this.playerService = playerService;
	}

	@Autowired // Automatic injection of beans
	protected SimpMessagingTemplate smesg;

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

    //i work bitch <3
    public void testy(Object dto){
         this.smesg.convertAndSendToUser("one", "/topic/testing", dto);
        //this.smesg.convertAndSend("/queue/testing", dto);
    }

}
