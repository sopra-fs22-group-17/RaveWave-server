package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import java.util.ArrayList;

public class QuestionDTO {
    private final String type = "question";
    private String question;
    private String previewURL;
    private String songTitle;
    private String playBackDuration;
    private ArrayList<AnswerOptions> answers;
    private int currentRound;
    private int totalRounds;

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public ArrayList<AnswerOptions> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerOptions> answers) {
        this.answers = answers;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getPlayBackDuration() {
        return playBackDuration;
    }

    public void setPlayBackDuration(String playBackDuration) {
        this.playBackDuration = playBackDuration;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }
}
