package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import java.util.ArrayList;

public class QuestionDTO {
    private String question;
    private String songID;
    private ArrayList<AnswerOptions> answers;



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

    public ArrayList<AnswerOptions> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerOptions> answers) {
        this.answers = answers;
    }

}
