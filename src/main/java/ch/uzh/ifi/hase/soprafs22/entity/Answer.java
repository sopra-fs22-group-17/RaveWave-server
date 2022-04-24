package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Timer;
@Entity
@Table(name = "Answer")

public class Answer {

    private String playerAnswer;
    private float answerTime;
    private boolean answerResult;
    private int answerId;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }

    public void setPlayerAnswer(String playerAnswer) {
        this.playerAnswer = playerAnswer;
    }

    public float getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(float answerTime) {
        this.answerTime = answerTime;
    }

    public boolean isAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(boolean answerResult) {
        this.answerResult = answerResult;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
