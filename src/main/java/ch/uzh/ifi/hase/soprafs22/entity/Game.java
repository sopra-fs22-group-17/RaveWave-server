package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.ArtistGame;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.Evaluator;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.GameType;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

public class Game {
    private final Logger log = LoggerFactory.getLogger(Game.class);

    // Game settings and getters and setters
    private RoundDuration roundDuration;
    private PlaybackDuration playbackDuration;
    private SongPool songGenre;
    private int gameRounds;
    private GameMode gameMode;

    private boolean startedGame;

    private ArrayList<GameType> gamePlan;
    private ArrayList<Answer> answers;
    private int lobbyId;
    private List<Player> players;
    private int currentGameRound;
    private String playlistId;
    private SpotifyService spotifyService;

    public Game(SpotifyService spotifyService, SongPool songGenre) {
        this.spotifyService = spotifyService;
        // mapEnumToPlaylist(songGenre);
        this.gamePlan = new ArrayList<>();
        this.songGenre = songGenre;
        this.currentGameRound = 0;
        this.startedGame = false;
        this.gameMode = GameMode.ARTISTGAME;
        this.answers = new ArrayList<Answer>();

    }


    public void updateGameSettings(GameSettingsDTO updatedSettings) {
        this.roundDuration = updatedSettings.getRoundDuration();
        this.playbackDuration = updatedSettings.getPlayBackDuration();
        this.songGenre = updatedSettings.getSongPool();
        this.gameRounds = updatedSettings.getGameRounds();
    }

    // TODO exception
    public void startGame() {
        fillGamePlan();
        this.startedGame = true;

    }

    public Question startNextTurn() {
        Question question = gamePlan.get(currentGameRound).getQuestion();
        currentGameRound++;
        this.answers.clear();
        return question;
    }

    private void startTimer(int timer) {
    }

    public boolean checkAnswers() {
        return true;
    }

    public void notifyPlayers() {
    }

    private void resetAnswers() {
        this.answers.clear();
    }

    public void addAnswers(Answer answer) {
        this.answers.add(answer);
    }

    public LeaderboardDTO endRound(List<Player> players){
        distributePoints(players);
        LeaderboardDTO leaderboardDTO = fillLeaderboard(players);
        if(this.currentGameRound == this.gameRounds){
           leaderboardDTO.setGameOver(true);
        }
        //TODO handle if player doesnt answer
        return leaderboardDTO;
    }

    private void distributePoints(List<Player> players) {
        Evaluator evaluator = new Evaluator();
        for (Player player : players) {
            Answer playerAnswer = new Answer();
            playerAnswer.setplayerGuess(5);
            playerAnswer.setPlayerId(player.getId());
            for (Answer answer : this.answers) {
                if (answer.getPlayerId().intValue() == player.getId().intValue()) {
                    playerAnswer.setplayerGuess(answer.getplayerGuess());
                    playerAnswer.setAnswerTime(answer.getAnswerTime());
                    break;
                }
            }

            Question currentQuestion = gamePlan.get(currentGameRound-1).getQuestion();

            int points = Evaluator.evaluation(playerAnswer, currentQuestion.getCorrectAnswer(), roundDuration);

            player.addToScore(points);

            if (points != 0) {
                player.setStreak(player.getStreak() + 1);
            } else {
                player.setStreak(0);
            }
        }
    }

    public void endGame(){}

    private void updateRaveWaver(){}

    public void fillGamePlan(){
        PlaylistTrack[] songs = spotifyService.getPlaylistsItems(songGenre.getPlaylistId());
        Random rand = new Random();
        int bound;
        ArrayList<Integer> pickedSongs = new ArrayList<>();
        if (songs.length < gameRounds){
            bound = songs.length;
        } else{ bound = gameRounds;}

        int i = 0;
        while( i < bound){
            int id = rand.nextInt(bound);
            while (pickedSongs.contains(id)){
                id = rand.nextInt(bound);
            }
            gamePlan.add(new ArtistGame(id, songs));
            pickedSongs.add(id);
            i++;
        }
    }

    private LeaderboardDTO fillLeaderboard(List<Player> players) {

        List<Player> sortedPlayers = sortPlayers(players);
        // List<Player> sortedPlayersPrevious = sortPlayersPreviousScore(players);

        ArrayList<LeaderboardEntry> playersRankingInformation = new ArrayList<>();

        int i = 1;
        for (Player player : sortedPlayers) {
            LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
            leaderboardEntry.setPlayerId(player.getId());
            leaderboardEntry.setPlayerName(player.getPlayerName());
            leaderboardEntry.setStreak(player.getStreak());
            leaderboardEntry.setTotalScore(player.getTotalScore());
            leaderboardEntry.setPlayerPosition(i);
            // leaderboardEntry.setPrevPosition(1);
            leaderboardEntry.setRoundScore(player.getRoundScore());
            i++;
            playersRankingInformation.add(leaderboardEntry);
        }

        LeaderboardDTO leaderboard = new LeaderboardDTO();

        leaderboard.setPlayers(playersRankingInformation);
        leaderboard.setGameOver(false);

        // leaderboard.setPrevPlayerPositions(sortPlayersPreviousScore(players));
        return leaderboard;
    }

    private List<Player> sortPlayers(List<Player> players) {
        int pos;
        Player temp;
        for (int i = 0; i < players.size(); i++) {
            pos = i;
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(j).getTotalScore() > players.get(pos).getTotalScore())                  //find the index of the minimum element
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

    private List<Player> sortPlayersPreviousScore(List<Player> players) {
        int pos;
        Player temp;
        for (int i = 0; i < players.size(); i++) {
            pos = i;
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(j).getTotalScore()-players.get(j).getRoundScore() > players.get(pos).getTotalScore() - players.get(pos).getRoundScore())                  //find the index of the minimum element
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

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public PlaybackDuration getPlaybackDuration() {
        return this.playbackDuration;
    }

    public int getGameRounds() {
        return this.gameRounds;
    }

    public RoundDuration getRoundDuration() {
        return this.roundDuration;
    }

    public SongPool getSongPool() {
        return this.songGenre;
    }
}