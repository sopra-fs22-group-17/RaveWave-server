package ch.uzh.ifi.hase.soprafs22.rest.dto;

import javax.persistence.Column;

public class PlayerPostDTO {

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    private String playerName;


}
