package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.Timer;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.ArtistGame;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.GameSettingsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.GameType;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

public class Game {
    private final Logger log = LoggerFactory.getLogger(Game.class);

    //Game settings and getters and setters
    private RoundDuration roundDuration;
    private PlaybackDuration playbackDuration;
    private SongPool songPool;
    private int gameRounds;

    public void updateGameSettings(GameSettingsDTO updatedSettings){
        this.roundDuration = updatedSettings.getRoundDuration();
        this.playbackDuration = updatedSettings.getPlayBackDuration();
        this.songPool = updatedSettings.getSongPool();
        this.gameRounds = updatedSettings.getGameRounds();
    }


    private ArrayList<GameType> gamePlan;
    private int lobbyId;
    private ArrayList<Player> players;
    private int currentGameRound;
    private String playlistId;
    private SpotifyService spotifyService;

    //TODO: second constructor just for debugging purpose
    //TODO: gameType
    public Game(SpotifyService spotifyService, SongPool songGenre){
        this.spotifyService = spotifyService;
        mapEnumToPlaylist(songGenre);
        this.gamePlan = new ArrayList<>();
        fillGamePlan();

        this.currentGameRound = 0;
    }

    //TODO so far there is only one option which is swiss music
    private void mapEnumToPlaylist(SongPool songGenre){
        if(songGenre.equals(SongPool.SWITZERLAND)){
            this.playlistId = "37i9dQZEVXbJiyhoAPEfMK";
        }else if(songGenre.equals(SongPool.COUNTRY)){
            this.playlistId = "37i9dQZF1DX1lVhptIYRda";
        }else if(songGenre.equals(SongPool.TOPHITSOFTHEDAY)){
            this.playlistId = "37i9dQZF1DXcBWIGoYBM5M";
        }else if(songGenre.equals(SongPool.ROCK)){
            this.playlistId = "37i9dQZF1DWXRqgorJj26U";
        }
        else if(songGenre.equals(SongPool.RNB)){
            this.playlistId = "37i9dQZF1DX4SBhb3fqCJd";
        }
        else if(songGenre.equals(SongPool.HIPHOP)){
            this.playlistId = "";
        }
        else if(songGenre.equals(SongPool.POP)){
            this.playlistId = "";
        }
        else if(songGenre.equals(SongPool.LATINO)){
            this.playlistId = "";
        }
        else if(songGenre.equals(SongPool.PERSONAL)){
            this.playlistId = "";
        }
        else{
            this.playlistId = "37i9dQZEVXbJiyhoAPEfMK";
        }

    }

    public Game(Timer roundDuration, Timer playBackDuration, GameMode gameMode, SongPool songPool){
    }

    public void startGame(){}

    public Question startNextTurn(){
        Question question = gamePlan.get(currentGameRound).getQuestion();
        currentGameRound++;
        return question;
    }

    private void startTimer(Integer timer){}

    public boolean checkAnswers(){return true;}

    public void notifyPlayers(){}

    public void endGameTurn(){}

    public void endGame(){}

    private void updateRaveWaver(){}

    public void fillGamePlan(){
        PlaylistTrack[] songs = spotifyService.getPlaylistsItems(playlistId);
        for(int i = 0; i < songs.length; i++){
            gamePlan.add(new ArtistGame(i, songs));
        }


    }

}

