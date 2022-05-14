package ch.uzh.ifi.hase.soprafs22.rest.dto;

public class PlayerPostDTO {

    private String playerName;

    private long raveWaverId;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getRaveWaverId() {
        return raveWaverId;
    }

    public void setRaveWaverId(long raveWaverId) {
        this.raveWaverId = raveWaverId;
    }


}
