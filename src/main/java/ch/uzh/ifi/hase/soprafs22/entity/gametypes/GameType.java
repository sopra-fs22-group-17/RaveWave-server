package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.entity.Question;

public interface GameType {

    void generateQuestion();

    Question getQuestion();

    int getCorrectAnswer();

}
