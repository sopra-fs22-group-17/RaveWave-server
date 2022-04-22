package ch.uzh.ifi.hase.soprafs22.websockets.dto;

import java.util.ArrayList;

public class QuestionDTO {
    private String Question;
    private ArrayList<String> SpotifyIds;
    private ArrayList<String> Artists;
    private ArrayList<String> SongNames;
    private ArrayList<String> Album;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public ArrayList<String> getSpotifyIds() {
        return SpotifyIds;
    }

    public void setSpotifyIds(ArrayList<String> spotifyIds) {
        SpotifyIds = spotifyIds;
    }

    public ArrayList<String> getArtists() {
        return Artists;
    }

    public void setArtists(ArrayList<String> artists) {
        Artists = artists;
    }

    public ArrayList<String> getSongNames() {
        return SongNames;
    }

    public void setSongNames(ArrayList<String> songNames) {
        SongNames = songNames;
    }

    public ArrayList<String> getAlbum() {
        return Album;
    }

    public void setAlbum(ArrayList<String> album) {
        Album = album;
    }


}
