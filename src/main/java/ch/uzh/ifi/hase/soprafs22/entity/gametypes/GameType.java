package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.entity.Question;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.ArrayList;

public interface GameType {

    void generateQuestion();

    Question getQuestion();

    int getCorrectAnswer();

}
