package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.List;

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

    public void generateQuestion(){

    }
}
