package ch.uzh.ifi.hase.soprafs22.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import ch.uzh.ifi.hase.soprafs22.service.GameService;

@Controller
public class GameController {
    Logger log = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

}
