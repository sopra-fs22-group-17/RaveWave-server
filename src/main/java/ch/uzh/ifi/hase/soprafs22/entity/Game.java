package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.List;
import java.util.Timer;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.GameType;

public class Game {
    private final Logger log = LoggerFactory.getLogger(Game.class);

    private RoundDuration roundDuration;
    private PlaybackDuration playbackDuration;
    private SongPool songPool;
    private List<GameType> gamePlan;
    private int lobbyId;
    private List<Player> players;
    private int gameRound;

    public void Game(Timer roundDuration, Timer playBackDuration, GameMode gameMode, SongPool songPool){}

    public void startGame(int lobbyId){}

    public void startNextTurn(int lobbyId){}

    private void startTimer(int timer){}

    public boolean checkAnswers(int lobbyId){return true;}

    public void notifyPlayers(int lobbyId){}

    public void endGameTurn(int lobbyId){}

    public void endGame(int lobbyId){}

    private void updateRaveWaver(){}
}

