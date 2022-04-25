package ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming;

import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;

import java.util.ArrayList;

public class GameSettingsDTO {
    //private int lobbyID;
    private RoundDuration roundDuration;
    private PlaybackDuration playBackDuration;
    private SongPool songPool;
    private int gameRounds;
    //private ArrayList<String> gamePlan;

/*
    public int getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }
*/
    public RoundDuration getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(RoundDuration roundDuration) {
        this.roundDuration = roundDuration;
    }

    public PlaybackDuration getPlayBackDuration() {
        return playBackDuration;
    }

    public void setPlayBackDuration(PlaybackDuration playBackDuration) {
        this.playBackDuration = playBackDuration;
    }

    public SongPool getSongPool() {
        return songPool;
    }

    public void setSongPool(SongPool songPool) {
        this.songPool = songPool;
    }

    public int getGameRounds() {
        return gameRounds;
    }

    public void setGameRounds(int gameRounds) {
        this.gameRounds = gameRounds;
    }

    /*
    public ArrayList<String> getGamePlan() {
        return gamePlan;
    }

    public void setGamePlan(ArrayList<String> gamePlan) {
        this.gamePlan = gamePlan;
    }*/





}
