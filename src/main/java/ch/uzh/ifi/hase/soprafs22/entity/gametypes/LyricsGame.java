package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.entity.Question;

public class LyricsGame implements GameType {
    private Question question;
    private Answer answer;

    @Override
    public Question displayQuestion() {
        return null;
    }

    @Override
    public void generateQuestion() {

    }

    @Override
    public String getQuestion() {
        return null;
    }
/*
    @Override
    public Answer getCorrectAnswer() {
        return null;
    }*/
}
