package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String question;
    private String previewUrl;
    private List<String> answers;
    private int correctAnswer;
    private GameMode gamemode;
    private ArrayList<String> albumCovers;

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String songId) {
        this.previewUrl = songId;
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

    public GameMode getGamemode() {
        return gamemode;
    }

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public ArrayList<String> getAlbumCovers() {
        return albumCovers;
    }

    public void setAlbumCovers(ArrayList<String> albumCovers) {
        this.albumCovers = albumCovers;
    }
}
