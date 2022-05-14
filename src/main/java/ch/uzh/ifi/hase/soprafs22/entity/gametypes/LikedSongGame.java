package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.midi.Track;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;

public class LikedSongGame implements GameType {
    private final Question question;

    private final ArrayList<Song> songs;
    private final int songToPick;
    private final ArrayList<Track> answerSongs;
    private final ArrayList<Player> playersSpotify;

    public LikedSongGame(int songToPick, ArrayList<Song> songs, List<Player> players) {
        this.question = new Question();
        this.songs = songs;
        this.songToPick = songToPick;
        this.answerSongs = new ArrayList<Track>();
        this.playersSpotify = pickPlayersWithSpotify(players);
        generateQuestion();
    }

    public ArrayList<Player> pickPlayersWithSpotify(List<Player> players) {
        ArrayList<Player> spotPlayer = new ArrayList<>();
        for (Player player : players) {
            if (player.getRaveWaverId() != null) {
                spotPlayer.add(player);
            }
        }
        return spotPlayer;
    }

    @Override
    public void generateQuestion() {
        question.setQuestion("Guess which player liked/listened to this song");
        question.setPreviewUrl(songs.get(songToPick).getTrack().getPreviewUrl());

        String correctAnswer = songs.get(songToPick).getPlayerName();

        ArrayList<String> answers = new ArrayList<String>();

        ArrayList<Integer> wrongAnswersIndex = new ArrayList<>();

        for (int i = 0; i < songs.size(); i++) {
            wrongAnswersIndex.add(i);
        }

        Random rand;
        rand = new Random();
        int a = 3;
        for (int i = 0; i < a; i++) {
            // pick a random number to compute the wrong answers
            int wrongAnswerIndex = wrongAnswersIndex.remove(rand.nextInt(wrongAnswersIndex.size()));

            // ensures that there will never be the same answer twice
            while (wrongAnswerIndex == songToPick && wrongAnswersIndex.size() > 0) {
                wrongAnswerIndex = wrongAnswersIndex.remove(rand.nextInt(wrongAnswersIndex.size()));
            }
            StringBuilder answer = new StringBuilder();
            for (ArtistSimplified artist : songs.get(wrongAnswerIndex).getTrack().getArtists()) {
                answer.append(artist.getName());
                answer.append(", ");
            }
            answer.delete(answer.length() - 2, answer.length());

            if (answers.contains(answer.toString())) {
                a++;
            } else {
                answers.add(answer.toString());
            }
            answerSongs.add(songs.get(wrongAnswerIndex).getTrack());
        }

        int correctAnswerIndex = rand.nextInt(4);
        answers.add(correctAnswerIndex, correctAnswer.toString());
        answerSongs.add(correctAnswerIndex, songs.get(songToPick).getTrack());

    }

    @Override
    public Question getQuestion() {
        return null;
    }

    @Override
    public int getCorrectAnswer() {
        return 1;
    }

}
