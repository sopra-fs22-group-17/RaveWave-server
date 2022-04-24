package ch.uzh.ifi.hase.soprafs22.websockets.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Answer;
import ch.uzh.ifi.hase.soprafs22.entity.Player;

public class AnswerDTO {

    private Player player;
    private Answer answer;
    private String answerTime;
    private int answerId;

    public ch.uzh.ifi.hase.soprafs22.entity.Player getPlayer() {
        return player;
    }

    public void setPlayer(ch.uzh.ifi.hase.soprafs22.entity.Player player) {
        player = player;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        answer = this.answer;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        answerTime = answerTime;
    }


}
