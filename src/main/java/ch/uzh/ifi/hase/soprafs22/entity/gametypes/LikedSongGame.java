package ch.uzh.ifi.hase.soprafs22.entity.gametypes;

import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.incoming.Answer;

public class LikedSongGame implements GameType {
    private Question question;
    private Answer answer;

    @Override
    public void generateQuestion() {

    }

    @Override
    public Question getQuestion() {
        return null;
    }

    @Override
    public int getCorrectAnswer() {
        return 1;
    }

}
