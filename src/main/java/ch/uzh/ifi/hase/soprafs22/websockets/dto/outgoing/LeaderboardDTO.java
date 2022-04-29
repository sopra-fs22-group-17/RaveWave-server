package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import ch.uzh.ifi.hase.soprafs22.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardDTO {

    private boolean isGameOver;

    private ArrayList<LeaderboardEntry> players;

    public ArrayList<LeaderboardEntry> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<LeaderboardEntry> players) {
        this.players = players;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }


}
