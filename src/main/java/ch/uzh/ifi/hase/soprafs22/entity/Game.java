package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.Timer;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.ArtistGame;
import ch.uzh.ifi.hase.soprafs22.spotify.SpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.GameType;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

public class Game {
    private final Logger log = LoggerFactory.getLogger(Game.class);

    private RoundDuration roundDuration;
    private PlaybackDuration playbackDuration;
    private SongPool songPool;
    private ArrayList<GameType> gamePlan;
    private int lobbyId;
    private ArrayList<Player> players;
    private int gameRound;
    private String playlistId;
    private SpotifyService spotifyService;

    //TODO: second constructor just for debugging purpose
    public Game(SpotifyService spotifyService, String playlistId){
        this.spotifyService = spotifyService;
        this.playlistId = playlistId;
        this.gamePlan = new ArrayList<>();
        fillGamePlan();
        for(int i = 0; i < gamePlan.size(); i++){
            System.out.println(gamePlan.get(i).getQuestion());
        }

    }
    public Game(Timer roundDuration, Timer playBackDuration, GameMode gameMode, SongPool songPool){
    }

    public void startGame(Integer lobbyId){}

    public void startNextTurn(Integer lobbyId){}

    private void startTimer(Integer timer){}

    public boolean checkAnswers(Integer lobbyId){return true;}

    public void notifyPlayers(Integer lobbyId){}

    public void endGameTurn(Integer lobbyId){}

    public void endGame(Integer lobbyId){}

    private void updateRaveWaver(){}


    public void fillGamePlan(){
        PlaylistTrack[] songs = spotifyService.getPlaylistsItems(playlistId);
        for(int i = 0; i < songs.length; i++){
            gamePlan.add(new ArtistGame(i, songs));
        }


    }

}

