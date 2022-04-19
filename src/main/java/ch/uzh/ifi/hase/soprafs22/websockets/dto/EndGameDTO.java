package ch.uzh.ifi.hase.soprafs22.websockets.dto;

import java.util.ArrayList;

public class EndGameDTO {
    //Reihefolg bedütend i denne liste, evt. andere lösigsasatz nötig
    private ArrayList<String> PlayerPositions;
    private ArrayList<String> PrevPlayerPositions;
    private ArrayList<Integer> Scores;
    private ArrayList<Integer> TotalScores;

    public ArrayList<String> getPlayerPositions() {
        return PlayerPositions;
    }

    public void setPlayerPositions(ArrayList<String> playerPositions) {
        PlayerPositions = playerPositions;
    }

    public ArrayList<String> getPrevPlayerPositions() {
        return PrevPlayerPositions;
    }

    public void setPrevPlayerPositions(ArrayList<String> prevPlayerPositions) {
        PrevPlayerPositions = prevPlayerPositions;
    }

    public ArrayList<Integer> getScores() {
        return Scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        Scores = scores;
    }

    public ArrayList<Integer> getTotalScores() {
        return TotalScores;
    }

    public void setTotalScores(ArrayList<Integer> totalScores) {
        TotalScores = totalScores;
    }

    public ArrayList<Integer> getStreaks() {
        return Streaks;
    }

    public void setStreaks(ArrayList<Integer> streaks) {
        Streaks = streaks;
    }

    public ArrayList<String> getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(ArrayList<String> profilePicture) {
        ProfilePicture = profilePicture;
    }

    private ArrayList<Integer> Streaks;
    //welle Datetyp?
    private ArrayList<String> ProfilePicture;


}
