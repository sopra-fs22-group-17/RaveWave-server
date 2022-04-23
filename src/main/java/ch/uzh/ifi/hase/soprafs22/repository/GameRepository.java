package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Game;

import java.util.HashMap;

public class GameRepository {

    private static final HashMap<String, Game> gameRepo = new HashMap<>();

    public static void addGame(String lobbyId, Game game) {
        gameRepo.put(lobbyId, game);
    }

    public static void removeGame(String lobbyId) {
    }

    public static Game findByLobbyId(String lobbyId) {
        return gameRepo.get(lobbyId);
    }
}