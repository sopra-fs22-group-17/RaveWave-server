package ch.uzh.ifi.hase.soprafs22.rest.dto;

import javax.persistence.Column;

public class PlayerPostDTO {

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(long lobbyId) {
        this.lobbyId = lobbyId;
    }

    private String playerName;

    private long lobbyId;
}
