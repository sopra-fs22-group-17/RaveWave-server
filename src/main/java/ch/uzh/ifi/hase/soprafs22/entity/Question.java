package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.List;

public class Question {

    private String question;
    private String songID;
    private List<String> answers;
    private int correctAnswer;

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songId) {
        this.songID = songId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }


    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


}
