package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import java.util.ArrayList;

public class LeaderboardDTO {
    //Reihefolg bedütend i denne liste, evt. andere lösigsasatz nötig
    private ArrayList<String> PlayerPositions;
    private ArrayList<String> PrevPlayerPositions;
    private int[] Scores;
    private int[] TotalScores;

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

    public int[] getScores() {
        return Scores;
    }

    public void setScores(int[] scores) {
        Scores = scores;
    }

    public int[] getTotalScores() {
        return TotalScores;
    }

    public void setTotalScores(int[] totalScores) {
        TotalScores = totalScores;
    }

    public int[] getStreaks() {
        return Streaks;
    }

    public void setStreaks(int[] streaks) {
        Streaks = streaks;
    }

    public ArrayList<String> getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(ArrayList<String> profilePicture) {
        ProfilePicture = profilePicture;
    }

    private int[] Streaks;
    //welle Datetyp?
    private ArrayList<String> ProfilePicture;


}
