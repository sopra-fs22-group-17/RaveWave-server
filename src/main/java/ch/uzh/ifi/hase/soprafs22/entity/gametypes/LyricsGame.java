package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.entity.Question;

import java.util.ArrayList;
import java.util.List;

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
    public Question getQuestion() {
        return null;
    }

    @Override
    public String getCorrectAnswer() {
        return null;
    }

}
