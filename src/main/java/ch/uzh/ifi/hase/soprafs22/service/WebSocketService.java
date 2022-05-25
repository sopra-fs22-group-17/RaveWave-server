package ch.uzh.ifi.hase.soprafs22.service;


import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void sendMessageToClients(String destination, Object dto) {
        this.smesg.convertAndSend(destination, dto);

    }


}
