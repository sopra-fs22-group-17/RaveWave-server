package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.GameMode;
import ch.uzh.ifi.hase.soprafs22.constant.PlaybackDuration;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String question;
    private String previewUrl;
    private List<String> answers;
    private int correctAnswer;
    private GameMode gamemode;
    private ArrayList<String> picture;
    private PlaybackDuration playbackDuration;
    private String songTitle;
    private String artist;
    private String spotifyLink;

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

    /*
    publ

    ic GameMode getGamemode() {
        return gamemode;
    }
*/
    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public ArrayList<String> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<String> picture) {
        this.picture = picture;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public PlaybackDuration getPlaybackDuration() {
        return playbackDuration;
    }

    public void setPlaybackDuration(PlaybackDuration playbackDuration) {
        this.playbackDuration = playbackDuration;
    }

    public String getSpotifyLink() {
        return spotifyLink;
    }

    public void setSpotifyLink(String spotifyLink) {
        this.spotifyLink = spotifyLink;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
