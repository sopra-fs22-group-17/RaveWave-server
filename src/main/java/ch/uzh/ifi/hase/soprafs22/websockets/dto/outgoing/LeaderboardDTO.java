package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import ch.uzh.ifi.hase.soprafs22.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardDTO {

    private ArrayList<LeaderboardEntry> players;

    public ArrayList<LeaderboardEntry> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<LeaderboardEntry> players) {
        this.players = players;
    }



}
