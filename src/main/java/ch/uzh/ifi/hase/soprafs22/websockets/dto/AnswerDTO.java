package ch.uzh.ifi.hase.soprafs22.websockets.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Player;

public class AnswerDTO {

    private Player Player;
    private int Answer;
    private String AnswerTime;

    public ch.uzh.ifi.hase.soprafs22.entity.Player getPlayer() {
        return Player;
    }

    public void setPlayer(ch.uzh.ifi.hase.soprafs22.entity.Player player) {
        Player = player;
    }

    public int getAnswer() {
        return Answer;
    }

    public void setAnswer(Integer answer) {
        Answer = answer;
    }

    public String getAnswerTime() {
        return AnswerTime;
    }

    public void setAnswerTime(String answerTime) {
        AnswerTime = answerTime;
    }


}
