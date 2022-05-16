package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.hc.core5.http.ParseException;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;
import ch.uzh.ifi.hase.soprafs22.constant.RoundDuration;
import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.controller.LobbyController;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.ArtistGame;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.GameType;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.LikedSongGame;
import ch.uzh.ifi.hase.soprafs22.entity.gametypes.SongTitleGame;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import ch.uzh.ifi.hase.soprafs22.utils.Evaluator;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.GameSettingsDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.LeaderboardEntry;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    private final ArrayList<GameType> gamePlan;
    private final ArrayList<Answer> answers;
    private final SpotifyService spotifyService;
    private final Random rand;
    private GameMode gameMode;
    private RoundDuration roundDuration;
    private PlaybackDuration playbackDuration;
    private SongPool songGenre;
    private int gameRounds;
    private int currentGameRound;
    private final RaveWaverRepository raveWaverRepository;
    Logger log = LoggerFactory.getLogger(Game.class);

    public Game(SpotifyService spotifyService, SongPool songGenre, RaveWaverRepository raveWaverRepository) {
        this.spotifyService = spotifyService;
        this.gamePlan = new ArrayList<>();
        this.songGenre = songGenre;
        this.currentGameRound = 0;
        this.gameMode = GameMode.ARTISTGAME;
        this.answers = new ArrayList<>();
        this.gameRounds = 15;
        this.rand = new Random();
        this.raveWaverRepository = raveWaverRepository;

    }

    public void updateGameSettings(GameSettingsDTO updatedSettings) {
        this.roundDuration = updatedSettings.getRoundDuration();
        this.playbackDuration = updatedSettings.getPlayBackDuration();
        this.songGenre = updatedSettings.getSongPool();
        this.gameRounds = updatedSettings.getGameRounds();
        this.gameMode = updatedSettings.getGameMode();
    }

    public void startGame(List<Player> players) {
        for (Player player : players) {
            log.info("Player: {}", player.getPlayerName());
        }
        // refreshes all the Spotify access tokens of all the RaveWavers in the lobby
        refreshAccessTokens(players);
        fillGamePlan(players);

    }

    private void refreshAccessTokens(List<Player> players) {
        for (Player player : players) {
            if (player.getRaveWaverId() != 0) {
                RaveWaver raveWaver = raveWaverRepository.findById(player.getRaveWaverId()).get();
                spotifyService.authorizationCodeRefresh(raveWaver);
            }
        }

    }

    public Question startNextTurn() {
        Question question = gamePlan.get(currentGameRound).getQuestion();
        question.setPlaybackDuration(playbackDuration);
        currentGameRound++;
        this.answers.clear();
        return question;
    }

    public void addAnswers(Answer answer) {
        this.answers.add(answer);
    }

    public LeaderboardDTO endRound(List<Player> players) {
        distributePoints(players);
        LeaderboardDTO leaderboardDTO = fillLeaderboard(players);
        leaderboardDTO.setGameOver(this.currentGameRound == this.gameRounds);
        leaderboardDTO.setArtist(gamePlan.get(currentGameRound - 1).getQuestion().getAnswers()
                .get(gamePlan.get(currentGameRound - 1).getQuestion().getCorrectAnswer() - 1));
        leaderboardDTO.setCoverUrl(gamePlan.get(currentGameRound - 1).getQuestion().getAlbumCovers()
                .get(gamePlan.get(currentGameRound - 1).getQuestion().getCorrectAnswer() - 1));
        leaderboardDTO.setSongTitle(gamePlan.get(currentGameRound - 1).getQuestion().getSongTitle());
        return leaderboardDTO;
    }

    private void distributePoints(List<Player> players) {
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

            Question currentQuestion = gamePlan.get(currentGameRound - 1).getQuestion();

            int points = Evaluator.evaluation(playerAnswer, currentQuestion.getCorrectAnswer(), roundDuration);

            player.addToScore(points);

            if (points != 0) {
                player.setStreak(player.getStreak() + 1);
            } else {
                player.setStreak(0);
            }
        }
    }

    public void fillGamePlan(List<Player> players) {
        ArrayList<Song> songs = new ArrayList<>();
        if (this.songGenre == SongPool.USERSTOPTRACKS) {
            for (Player player : players) {
                Long raveWaverId = player.getRaveWaverId();
                if (raveWaverId != null) {
                    songs.addAll(spotifyService.getPersonalizedPlaylistsItems(raveWaverId));
                }
            }
        } else if (this.songGenre == SongPool.USERSSAVEDTRACKS) {
            for (Player player : players) {
                Long raveWaverId = player.getRaveWaverId();
                if (raveWaverId != null) {
                    songs.addAll(spotifyService.getSavedTrackItems(raveWaverId));
                }
            }
        } else {
            songs.addAll(spotifyService.getPlaylistsItems(songGenre.getPlaylistId()));
        }
        int bound;
        ArrayList<Integer> pickedSongs = new ArrayList<>();
        if (songs.size() < gameRounds) {
            bound = songs.size();
        }

        else {
            bound = gameRounds;
        }

        int i = 0;
        while (i < bound) {
            int id = rand.nextInt(songs.size());
            while (pickedSongs.contains(id) || songs.get(id).getTrack().getPreviewUrl() == null
                    || songs.get(id).getTrack().getAlbum().getImages()[1] == null) {
                id = rand.nextInt(songs.size());
            }
            if (this.gameMode == GameMode.ARTISTGAME) {
                gamePlan.add(new ArtistGame(id, songs, spotifyService));
            } else if (this.gameMode == GameMode.SONGTITLEGAME) {
                gamePlan.add(new SongTitleGame(id, songs));
            } else if (this.gameMode == GameMode.LIKEDSONGGAME) {
                gamePlan.add(new LikedSongGame(id, songs, players));
            } else {
                gamePlan.add(new ArtistGame(id, songs, spotifyService));
                System.out.println("didn't work");
            }
            pickedSongs.add(id);
            i++;
        }
    }

    public LeaderboardDTO fillLeaderboard(List<Player> players) {

        List<Player> sortedPlayers = sortPlayers(players);

        ArrayList<LeaderboardEntry> playersRankingInformation = new ArrayList<>();

        int i = 1;
        for (Player player : sortedPlayers) {
            LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
            leaderboardEntry.setPlayerId(player.getId());
            leaderboardEntry.setPlayerName(player.getPlayerName());
            leaderboardEntry.setStreak(player.getStreak());
            leaderboardEntry.setTotalScore(player.getTotalScore());
            leaderboardEntry.setPlayerPosition(i);
            leaderboardEntry.setRoundScore(player.getRoundScore());
            leaderboardEntry.setProfilePicture(player.getProfilePicture());
            i++;
            playersRankingInformation.add(leaderboardEntry);

        }

        LeaderboardDTO leaderboard = new LeaderboardDTO();
        leaderboard.setPlayers(playersRankingInformation);

        return leaderboard;
    }

    public List<Player> sortPlayers(List<Player> players) {
        int pos;
        Player temp;
        for (int i = 0; i < players.size(); i++) {
            pos = i;
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(j).getTotalScore() > players.get(pos).getTotalScore()) // find the index of the minimum
                // element
                {
                    pos = j;
                }
            }
            temp = players.get(pos); // swap the current element with the minimum element

            players.set(pos, players.get(i));
            players.set(i, temp);
        }
        return players;
    }

    // TODO in progress
    /*
     * private List<Player> sortPlayersPreviousScore(List<Player> players) {
     * int pos;
     * Player temp;
     * for (int i = 0; i < players.size(); i++) {
     * pos = i;
     * for (int j = i + 1; j < players.size(); j++) {
     * if (players.get(j).getTotalScore() - players.get(j).getRoundScore() >
     * players.get(pos).getTotalScore() - players.get(pos).getRoundScore()) //find
     * the index of the minimum element
     * {
     * pos = j;
     * }
     * }
     * temp = players.get(pos); //swap the current element with the minimum element
     * players.set(pos, players.get(i));
     * players.set(i, temp);
     * }
     * return players;
     * }
     */
    public List<Answer> getListOfAnswers() {
        return this.answers;
    }

    public GameSettingsDTO getGameSettings() {
        GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
        gameSettingsDTO.setGameRounds(this.gameRounds);
        gameSettingsDTO.setGameMode(this.gameMode);
        gameSettingsDTO.setSongPool(this.songGenre);
        gameSettingsDTO.setPlayBackDuration(this.playbackDuration);
        gameSettingsDTO.setRoundDuration(this.roundDuration);

        return gameSettingsDTO;
    }

    public boolean hasStarted() {
        return currentGameRound != 0;
    }

    public void generateAvatar(List<Player> players) throws IOException, ParseException, SpotifyWebApiException {

        for (Player player : players) {
            if (player.getRaveWaverId() != 0) {
                Optional<RaveWaver> raveWaver = raveWaverRepository.findById(player.getRaveWaverId());
                player.setProfilePicture(raveWaver.get().getProfilePicture());
            } else {
                player.setProfilePicture("https://robohash.org/" + player.getPlayerName() + ".png");
            }

        }
    }

}