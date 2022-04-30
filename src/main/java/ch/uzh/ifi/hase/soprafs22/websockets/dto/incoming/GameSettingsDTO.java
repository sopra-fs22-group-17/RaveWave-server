package ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;

public class GameSettingsDTO {

    private RoundDuration roundDuration;
    private PlaybackDuration playBackDuration;
    private SongPool songPool;
    private int gameRounds;
    private GameMode gameMode;

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

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


}
