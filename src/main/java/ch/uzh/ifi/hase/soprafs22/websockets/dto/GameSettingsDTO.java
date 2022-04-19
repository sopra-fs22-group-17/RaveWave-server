package ch.uzh.ifi.hase.soprafs22.websockets.dto;

import java.util.ArrayList;

public class GameSettingsDTO {

    private String LobbyId;
    private int RoundDuration;
    private int PlayBackDuration;
    private String SongPool;
    private int GameRounds;
    private ArrayList<String> GamePlan;

    public String getLobbyId() {
        return LobbyId;
    }

    public void setLobbyId(String lobbyId) {
        LobbyId = lobbyId;
    }

    public int getRoundDuration() {
        return RoundDuration;
    }

    public void setRoundDuration(Integer roundDuration) {
        RoundDuration = roundDuration;
    }

    public int getPlayBackDuration() {
        return PlayBackDuration;
    }

    public void setPlayBackDuration(Integer playBackDuration) {
        PlayBackDuration = playBackDuration;
    }

    public String getSongPool() {
        return SongPool;
    }

    public void setSongPool(String songPool) {
        SongPool = songPool;
    }

    public int getGameRounds() {
        return GameRounds;
    }

    public void setGameRounds(Integer gameRounds) {
        GameRounds = gameRounds;
    }

    public ArrayList<String> getGamePlan() {
        return GamePlan;
    }

    public void setGamePlan(ArrayList<String> gamePlan) {
        GamePlan = gamePlan;
    }


}
