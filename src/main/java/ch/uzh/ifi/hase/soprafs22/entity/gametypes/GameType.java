package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.entity.Question;

import java.util.ArrayList;

public interface GameType {

    void generateQuestion();

    Question getQuestion();

    ArrayList<String> getPictures();

}
