package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtistGame implements GameType {
    private Question question;
    //private Answer answer; //überflüssig??

    private PlaylistTrack[] songs;
    private int songToPick;



    public ArtistGame(int songToPick, PlaylistTrack[] songs){
        //TODO map songPools onto playlistIds
        this.question = new Question();
        this.songs = songs;
        this.songToPick = songToPick;

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

        //question.setCorrectAnswer(songs[songToPick].getTrack().getName());
        String correctAnswer = ((Track) songs[songToPick].getTrack()).getArtists()[0].getName();
        //question.setCorrectAnswer(correctAnswer);

        ArrayList<String> answers = new ArrayList<String>();

        ArrayList<Integer> wrongAnswersIndex = new ArrayList<>();

        for(int i = 0; i < songs.length; i++){
            wrongAnswersIndex.add(i);
        }

        Random rand = new Random();

        for(int i = 0; i < 3; i++){
            //pick a random number to compute the wrong answers
            int wrongAnswerIndex = wrongAnswersIndex.remove(rand.nextInt(wrongAnswersIndex.size()));

            //ensures that there will never be the same answer twice
            while(wrongAnswerIndex == songToPick && wrongAnswersIndex.size() > 0){
                wrongAnswerIndex = wrongAnswersIndex.remove(rand.nextInt(wrongAnswersIndex.size()));
            }
            answers.add(((Track) songs[wrongAnswerIndex].getTrack()).getArtists()[0].getName());
        }

        int correctAnswerIndex = rand.nextInt(4);
        answers.add(correctAnswerIndex, correctAnswer);

        question.setAnswers(answers);
        question.setCorrectAnswer(correctAnswerIndex);
        question.setGamemode(GameMode.ARTISTGAME);

    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public int getCorrectAnswer() {
        return question.getCorrectAnswer();
    }


}
