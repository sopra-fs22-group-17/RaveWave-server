package ch.uzh.ifi.hase.soprafs22.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;

/**
 * GameService
 */
@Service
@Transactional
public class GameService {
    Logger log = LoggerFactory.getLogger(GameService.class);

    private final PlayerRepository playerRepository;

    @Autowired
    public GameService(@Qualifier("PlayerRepository") PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

}