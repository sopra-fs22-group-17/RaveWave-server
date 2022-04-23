package ch.uzh.ifi.hase.soprafs22.websockets.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionDTO {
    private String question;
    private String songID;
    private List<String> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }


}
