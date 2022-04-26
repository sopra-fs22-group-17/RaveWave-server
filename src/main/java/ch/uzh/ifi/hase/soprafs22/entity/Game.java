package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.ArtistGame;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.Evaluator;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
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
    public Game(SpotifyService spotifyService, SongPool songGenre) {
        this.spotifyService = spotifyService;
        //mapEnumToPlaylist(songGenre);
        this.gamePlan = new ArrayList<>();
        this.songGenre = songGenre;
        this.currentGameRound = 0;
        this.startedGame = false;
    }

    public Game(Timer roundDuration, Timer playBackDuration, GameMode gameMode, SongPool songGenre) {
    }

    public void updateGameSettings(GameSettingsDTO updatedSettings) {
        this.roundDuration = updatedSettings.getRoundDuration();
        this.playbackDuration = updatedSettings.getPlayBackDuration();
        this.songGenre = updatedSettings.getSongPool();
        this.gameRounds = updatedSettings.getGameRounds();
    }

    //TODO exception
    public void startGame() {
        fillGamePlan();
        this.startedGame = true;

    }

    public Question startNextTurn() {
        Question question = gamePlan.get(currentGameRound).getQuestion();
        currentGameRound++;
        return question;
    }

    private void startTimer(int timer) {
    }

    public boolean checkAnswers() {
        return true;
    }

    public void notifyPlayers() {
    }

    public void endRound(List<Player> players){
        distributePoints(players);
    }

    private void distributePoints(List<Player> players){
        Evaluator evaluator = new Evaluator();
        for(Player player : players){
            Answer playerAnswer = player.getAnswers().get(currentGameRound);
            Question currentQuestion = gamePlan.get(currentGameRound).getQuestion();
            int points = evaluator.evaluation(playerAnswer, currentQuestion.getCorrectAnswer(), roundDuration);
            System.out.println(points);

            player.addToScore(points);

            if(points != 0){
                player.setStreak(player.getStreak() + 1);
            }else{
                player.setStreak(0);
            }
        }
    }

    public void endGame(){}

    private void updateRaveWaver(){}

    public void fillGamePlan(){
        PlaylistTrack[] songs = spotifyService.getPlaylistsItems(songGenre.getPlaylistId());
        for(int i = 0; i < songs.length && i < gameRounds; i++){
            gamePlan.add(new ArtistGame(i, songs));
        }
    }

    public void fillLeaderboard() {

        LeaderboardDTO leaderboard = new LeaderboardDTO();

        ArrayList<Player> orderedList = sortPlayers(players);
        leaderboard.setPlayerPositions(orderedList);

        for (int i = 0; i< players.size(); i++){
            leaderboard.setTotalScore(i,players.get(i).getTotalScore());
            leaderboard.setLastScore(i, players.get(i).getLastScore());
            leaderboard.setStreak(i, players.get(i).getStreak());
        }
        leaderboard.setPrevPlayerPositions(sortPlayersPreviousScore(players));
    }

    private ArrayList<Player> sortPlayers(ArrayList<Player> players) {
        int pos;
        Player temp;
        for (int i = 0; i < players.size(); i++) {
            pos = i;
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(j).getTotalScore() < players.get(pos).getTotalScore())                  //find the index of the minimum element
                {
                    pos = j;
                }
            }
            temp = players.get(pos);  //swap the current element with the minimum element
            players.set(pos, players.get(i));
            players.set(i, temp);
        }
        return players;
    }

    private ArrayList<Player> sortPlayersPreviousScore(ArrayList<Player> players) {
        int pos;
        Player temp;
        for (int i = 0; i < players.size(); i++) {
            pos = i;
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(j).getTotalScore()-players.get(j).getLastScore() < players.get(pos).getTotalScore() - players.get(pos).getLastScore())                  //find the index of the minimum element
                {
                    pos = j;
                }
            }
            temp = players.get(pos);  //swap the current element with the minimum element
            players.set(pos, players.get(i));
            players.set(i, temp);
        }
        return players;
    }
}

