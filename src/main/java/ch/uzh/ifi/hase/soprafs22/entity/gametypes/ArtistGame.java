package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.constant.SongPool;
import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.spotify.SpotifyService;
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
        question.setQuestion("Guess the song title");
        //store id of the song to be played
        question.setSongId(songs[songToPick].getTrack().getId());

        //question.setCorrectAnswer(songs[songToPick].getTrack().getName());
        question.setCorrectAnswer(((Track) songs[songToPick].getTrack()).getArtists()[0].getName());

        ArrayList<String> wrongAnswers = new ArrayList<String>();

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
            wrongAnswers.add(((Track) songs[wrongAnswerIndex].getTrack()).getArtists()[0].getName());
        }
        question.setWrongAnswers(wrongAnswers);

    }

    @Override
    public String getQuestion() {
        return question.getQuestion() + "\n" + question.getWrongAnswers() + "\n" + question.getCorrectAnswer();
    }
/*
    @Override
    public Answer getCorrectAnswer() {
        return null;
    }*/
}
