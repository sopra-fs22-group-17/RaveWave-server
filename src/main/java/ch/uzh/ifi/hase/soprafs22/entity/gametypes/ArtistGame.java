package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtistGame implements GameType {
    private Question question;
    //private Answer answer; //überflüssig??

    private PlaylistTrack[] songs;
    private int songToPick;
    private ArrayList<Track> answerSongs;


    public ArtistGame(int songToPick, PlaylistTrack[] songs) {
        //TODO map songPools onto playlistIds
        this.question = new Question();
        this.songs = songs;
        this.songToPick = songToPick;
        this.answerSongs = new ArrayList<Track>();
        generateQuestion();

    }

    @Override
    public Question displayQuestion() {
        return null;
    }

    @Override
    public void generateQuestion() {
        //TODO get all artists if there are multiple ones?
        question.setQuestion("Guess the song artist");
        //store id of the song to be played
        question.setSongID(songs[songToPick].getTrack().getId());

        StringBuilder correctAnswer= new StringBuilder();
        for(ArtistSimplified artist : ((Track) songs[songToPick].getTrack()).getArtists()) {
            correctAnswer.append(artist.getName());
            correctAnswer.append(", ");
        }
        correctAnswer.delete(correctAnswer.length()-2, correctAnswer.length());

        ArrayList<String> answers = new ArrayList<String>();

        ArrayList<Integer> wrongAnswersIndex = new ArrayList<>();

        for (int i = 0; i < songs.length; i++) {
            wrongAnswersIndex.add(i);
        }

        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            //pick a random number to compute the wrong answers
            int wrongAnswerIndex = wrongAnswersIndex.remove(rand.nextInt(wrongAnswersIndex.size()));

            //ensures that there will never be the same answer twice
            while (wrongAnswerIndex == songToPick && wrongAnswersIndex.size() > 0) {
                wrongAnswerIndex = wrongAnswersIndex.remove(rand.nextInt(wrongAnswersIndex.size()));
            }
            StringBuilder answer = new StringBuilder();
            for(ArtistSimplified artist : ((Track) songs[wrongAnswerIndex].getTrack()).getArtists()) {
                answer.append(artist.getName());
                answer.append(", ");
            }
            answer.delete(answer.length()-2, answer.length());
            answers.add(answer.toString());

            answerSongs.add((Track) songs[wrongAnswerIndex].getTrack());
        }

        int correctAnswerIndex = rand.nextInt(4);
        answers.add(correctAnswerIndex, correctAnswer.toString());
        answerSongs.add(correctAnswerIndex,(Track) songs[songToPick].getTrack());

        question.setAnswers(answers);

        question.setCorrectAnswer(correctAnswerIndex);
        question.setGamemode(GameMode.ARTISTGAME);
        question.setAlbumCovers(getAllAnswersSongCovers());
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
