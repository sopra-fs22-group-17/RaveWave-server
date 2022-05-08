package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.Random;

public class AlbumCoverGame implements GameType {
    private final Question question;
    private final ArrayList<Track> songs;
    private final int songToPick;
    private final ArrayList<Track> answerSongs;

    public AlbumCoverGame(int songToPick, ArrayList<Track> songs) {
        this.question = new Question();
        this.songs = songs;
        this.songToPick = songToPick;
        this.answerSongs = new ArrayList<Track>();
        generateQuestion();
    }

    @Override
    public void generateQuestion() {
        question.setQuestion("Guess the album cover");
        question.setPreviewUrl(songs.get(songToPick).getPreviewUrl());

        String correctAnswer = songs.get(songToPick).getAlbum().getName();

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
            String answer = songs.get(wrongAnswerIndex).getAlbum().getName();

            if (answers.contains(answer)) {
                a++;
            } else {
                answers.add(answer);
            }
            answerSongs.add(songs.get(wrongAnswerIndex));
        }

        int correctAnswerIndex = rand.nextInt(4);
        answers.add(correctAnswerIndex, correctAnswer);
        answerSongs.add(correctAnswerIndex, songs.get(songToPick));

        question.setAnswers(answers);
        question.setCorrectAnswer(correctAnswerIndex + 1);
        question.setGamemode(GameMode.ALBUMCOVERGAME);
        question.setAlbumCovers(getSongCovers());
        question.setSongTitle(songs.get(songToPick).getName());

    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public int getCorrectAnswer() {
        return question.getCorrectAnswer();
    }

    @Override
    public ArrayList<String> getSongCovers(){
        ArrayList<String> albumCovers = new ArrayList<String>();

        for (int i = 0; i < 4; i++) {
            albumCovers.add(answerSongs.get(i).getAlbum().getImages()[1].getUrl());
        }
        return albumCovers;
    }


}
