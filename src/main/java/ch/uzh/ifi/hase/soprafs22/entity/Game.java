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
    private SongPool songGenre;
    private int gameRounds;

    private boolean startedGame;

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
        //mapEnumToPlaylist(songGenre);
        this.gamePlan = new ArrayList<>();
        this.songGenre = songGenre;
        this.currentGameRound = 0;
        this.startedGame = false;
    }

    public Game(Timer roundDuration, Timer playBackDuration, GameMode gameMode, SongPool songGenre){
    }

    public void updateGameSettings(GameSettingsDTO updatedSettings){
        this.roundDuration = updatedSettings.getRoundDuration();
        this.playbackDuration = updatedSettings.getPlayBackDuration();
        this.songGenre = updatedSettings.getSongPool();
        this.gameRounds = updatedSettings.getGameRounds();
    }

    //TODO exception
    public void startGame(){
        fillGamePlan();
        this.startedGame = true;

    }

    public Question startNextTurn(){
        Question question = gamePlan.get(currentGameRound).getQuestion();
        currentGameRound++;
        return question;
    }

    private void startTimer(int timer){}

    public boolean checkAnswers(){return true;}

    public void notifyPlayers(){}

    public void endGameTurn(){}

    public void endGame(){}

    private void updateRaveWaver(){}

    public void fillGamePlan(){
        PlaylistTrack[] songs = spotifyService.getPlaylistsItems(songGenre.getPlaylistId());
        for(int i = 0; i < songs.length && i < gameRounds; i++){
            gamePlan.add(new ArtistGame(i, songs));
        }


    }

}

