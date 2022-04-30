package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.entity.Question;

public interface GameType {
    Question displayQuestion();

    void generateQuestion();

    Question getQuestion();

    int getCorrectAnswer();
    //Answer getCorrectAnswer();
    //String fetchCorrectAnswer();
    //String generateWrongAnswer();

}
