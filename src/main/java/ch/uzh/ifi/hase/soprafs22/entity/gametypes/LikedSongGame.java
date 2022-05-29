package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LikedSongGame implements GameType {
    private final Question question;

    private final ArrayList<Song> songs;
    private final int songToPick;
    private final ArrayList<Player> playersSpotify;

    public LikedSongGame(int songToPick, ArrayList<Song> songs, List<Player> players) {
        this.question = new Question();
        this.songs = songs;
        this.songToPick = songToPick;
        this.playersSpotify = pickPlayersWithSpotify(players);
        generateQuestion();
    }

    public ArrayList<Player> pickPlayersWithSpotify(List<Player> players) {
        ArrayList<Player> spotPlayer = new ArrayList<>();
        for (Player player : players) {
            if (player.getRaveWaverId() != null && player.getRaveWaverId() != 0) {
                spotPlayer.add(player);
            }
        }
        return spotPlayer;
    }

    @Override
    public void generateQuestion() {
        question.setQuestion("Guess the liked song");
        question.setPreviewUrl(songs.get(songToPick).getTrack().getPreviewUrl());
        question.setSpotifyLink(songs.get(songToPick).getTrack().getExternalUrls().getExternalUrls().get("spotify"));

        String correctAnswer = songs.get(songToPick).getPlayerName();
        Player correctPlayer = findCorrectPlayer(correctAnswer);

        StringBuilder artists = new StringBuilder();
        for (ArtistSimplified artist : songs.get(songToPick).getTrack().getArtists()) {
            artists.append(artist.getName());
            artists.append(", ");
        }
        artists.delete(artists.length() - 2, artists.length());

        question.setArtist(artists.toString());

        ArrayList<Player> answers = new ArrayList<>();

        Random randomGenerator = new Random();

        ArrayList<Player> wrongAnswersPlayer = new ArrayList<>();
        for (Player player : playersSpotify) {
            if (!correctAnswer.equals(player.getPlayerName())) {
                wrongAnswersPlayer.add(player);
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                int wrongIndex = randomGenerator.nextInt(wrongAnswersPlayer.size());
                answers.add(wrongAnswersPlayer.get(wrongIndex));
                wrongAnswersPlayer.remove(wrongIndex);
            }
            catch (Exception e) {
                Player notAPlayer = createDummyPlayer();
                answers.add(notAPlayer);
            }
        }

        int correctAnswerIndex = randomGenerator.nextInt(4);
        answers.add(correctAnswerIndex, correctPlayer);
        question.setAnswers(stringifyAnswer(answers));
        question.setCorrectAnswer(correctAnswerIndex + 1);
        question.setGamemode(GameMode.LIKEDSONGGAME);
        question.setPictures(getUserProfilPictures(answers));
        question.setSongTitle(songs.get(songToPick).getTrack().getName());
        question.setCoverUrl(songs.get(songToPick).getTrack().getAlbum().getImages()[1].getUrl());
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    private ArrayList<String> stringifyAnswer(ArrayList<Player> players) {
        ArrayList<String> answers = new ArrayList<>();
        for (Player player : players) {
            try {
                answers.add(player.getPlayerName());
            }
            catch (Exception e) {
                Player notAPlayer = createDummyPlayer();
                answers.add(notAPlayer.getPlayerName());
            }
        }
        return answers;
    }

    private ArrayList<String> getUserProfilPictures(ArrayList<Player> players) {
        ArrayList<String> profilePictures = new ArrayList<>();
        for (Player player : players) {
            try {
                profilePictures.add(player.getProfilePicture());
            }
            catch (Exception e) {
                Player notAPlayer = createDummyPlayer();
                profilePictures.add(notAPlayer.getProfilePicture());
            }
        }
        return profilePictures;
    }

    private Player findCorrectPlayer(String playerName) {
        for (Player player : playersSpotify) {
            if (player.getPlayerName().equals(playerName)) {
                return player;
            }
        }
        return null;

    }

    @Override
    public ArrayList<String> getPictures() {
        return new ArrayList<>();
    }

    private Player createDummyPlayer() {
        Player player = new Player();
        player.setPlayerName("NotThePlayerYouAreLookingFor69");
        player.setProfilePicture(
                "https://preview.redd.it/6o6blcul5n841.jpg?auto=webp&s=ccfaf79f8c679b8d075131e67319d955cda25a30");

        return player;
    }

}
