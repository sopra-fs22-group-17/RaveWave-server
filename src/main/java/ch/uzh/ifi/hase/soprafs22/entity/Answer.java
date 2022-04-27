package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class Answer {

    private int playerGuess;
    private Long playerId;
    private float answerTime;

    public int getplayerGuess() {
        return playerGuess;
    }

    public void setplayerGuess(int id) {
        this.playerGuess = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public float getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(float answerTime) {
        this.answerTime = answerTime;
    }

}
