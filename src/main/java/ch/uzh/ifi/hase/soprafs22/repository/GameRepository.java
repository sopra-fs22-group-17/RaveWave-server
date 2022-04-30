package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Game;

import java.util.HashMap;

public class GameRepository {

    private static final HashMap<Integer, Game> gameRepo = new HashMap<>();

    public static void addGame(int lobbyId, Game game) {
        gameRepo.put(lobbyId, game);
    }

    public static void removeGame(int lobbyId) {
        gameRepo.remove(lobbyId);
    }

    public static Game findByLobbyId(int lobbyId) {
        return gameRepo.get(lobbyId);
    }
}