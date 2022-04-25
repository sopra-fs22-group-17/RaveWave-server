package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Answer")

public class Answer {

    private String playerAnswer;
    private float answerTime;
    private boolean answerResult;
    private int answerNr;
    private Long id;

    public boolean isCorrectAndUpdateAnswerResult(Answer correctAnswer) {
        this.answerResult = this.isEqualAnswerId(correctAnswer);
        return this.answerResult;
    }

    private boolean isEqualAnswerId(Answer correctAnswer) {
        return correctAnswer.getAnswerNr() == this.getAnswerNr();
    }

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

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerId) {
        this.answerNr = answerId;
    }
}
