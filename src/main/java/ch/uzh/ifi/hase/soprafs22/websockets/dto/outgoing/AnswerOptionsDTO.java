package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

public class AnswerOptionsDTO {
    private String answer;
    private int answerId;
    private String picture;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


}
