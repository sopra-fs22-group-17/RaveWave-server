package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.midi.Track;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;

import java.util.ArrayList;

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
            if (player.getRaveWaverId() != null) {
                System.out.println(player.getPlayerName());
                spotPlayer.add(player);
            }
        }
        return spotPlayer;
    }

    @Override
    public void generateQuestion() {
        question.setQuestion("Guess which player listened to this song");
        question.setPreviewUrl(songs.get(songToPick).getTrack().getPreviewUrl());

        String correctAnswer = songs.get(songToPick).getPlayerName();
        Player correctPlayer = findCorrectPlayer(correctAnswer);
        System.out.println(correctAnswer);

        ArrayList<Player> answers = new ArrayList<>();

        Random randomGenerator = new Random();

        ArrayList<Player> wrongAnswersPlayer = new ArrayList<>();
        for (Player player : playersSpotify) {
            if (!correctAnswer.equals(player.getPlayerName())) {
                wrongAnswersPlayer.add(player);
            }
        }

        for (int i = 0; i < 3; i++) {
            int wrongIndex = randomGenerator.nextInt(wrongAnswersPlayer.size());
            answers.add(wrongAnswersPlayer.get(wrongIndex));
            wrongAnswersPlayer.remove(wrongIndex);
        }

        int correctAnswerIndex = randomGenerator.nextInt(4);
        answers.add(correctAnswerIndex, correctPlayer);
        question.setAnswers(stringifyAnswer(answers));
        question.setCorrectAnswer(correctAnswerIndex + 1);
        question.setGamemode(GameMode.LIKEDSONGGAME);
        question.setAlbumCovers(getUserProfilPictures(answers));
        question.setSongTitle(songs.get(songToPick).getTrack().getName());

    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public int getCorrectAnswer() {
        return question.getCorrectAnswer();
    }

    private ArrayList<String> stringifyAnswer(ArrayList<Player> players) {
        ArrayList<String> answers = new ArrayList<>();
        for (Player player : players) {
            System.out.println(player.getPlayerName());
            answers.add(player.getPlayerName());
        }
        return answers;
    }

    private ArrayList<String> getUserProfilPictures(ArrayList<Player> players) {
        ArrayList<String> profilePictures = new ArrayList<>();
        for (Player player : players) {
            profilePictures.add(player.getProfilePicture());
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
    public ArrayList<String> getSongCovers() {
        return new ArrayList<String>();
    }

}
