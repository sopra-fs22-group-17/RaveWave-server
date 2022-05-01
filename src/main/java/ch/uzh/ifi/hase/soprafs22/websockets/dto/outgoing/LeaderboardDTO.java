package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import java.util.ArrayList;

public class LeaderboardDTO {
    private final String type = "result";
    private boolean isGameOver;
    private String coverUrl;
    private String songTitle;
    private String artist;
    private ArrayList<LeaderboardEntry> players;

    public String getType() {
        return type;
    }

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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}
