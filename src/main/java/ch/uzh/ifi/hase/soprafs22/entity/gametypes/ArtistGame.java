package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.Song;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.Random;

public class ArtistGame implements GameType {
    private final Question question;

    private final ArrayList<Song> songs;
    private final int songToPick;
    private final ArrayList<Track> answerSongs;

    public ArtistGame(int songToPick, ArrayList<Song> songs) {
        this.question = new Question();
        this.songs = songs;
        this.songToPick = songToPick;
        this.answerSongs = new ArrayList<Track>();
        generateQuestion();

    }

    @Override
    public void generateQuestion() {
        question.setQuestion("Guess the song artist");
        // store id of the song to be played
        question.setPreviewUrl(songs.get(songToPick).getTrack().getPreviewUrl());

        StringBuilder correctAnswer = new StringBuilder();
        for (ArtistSimplified artist : songs.get(songToPick).getTrack().getArtists()) {
            correctAnswer.append(artist.getName());
            correctAnswer.append(", ");
        }
        correctAnswer.delete(correctAnswer.length() - 2, correctAnswer.length());

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

        question.setAnswers(answers);
        question.setCorrectAnswer(correctAnswerIndex + 1);
        question.setGamemode(GameMode.ARTISTGAME);
        question.setAlbumCovers(getAllAnswersSongCovers());
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

    public ArrayList<String> getAllAnswersSongCovers() {
        ArrayList<String> albumCovers = new ArrayList<String>();

        for (int i = 0; i < 4; i++) {
            albumCovers.add(answerSongs.get(i).getAlbum().getImages()[1].getUrl());
        }
        return albumCovers;
    }
}
