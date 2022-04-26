package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.entity.Question;

import java.util.ArrayList;
import java.util.List;

public interface GameType {
    Question displayQuestion();
    void generateQuestion();
    Question getQuestion();
    int getCorrectAnswer();
    //Answer getCorrectAnswer();
    //String fetchCorrectAnswer();
    //String generateWrongAnswer();

}
