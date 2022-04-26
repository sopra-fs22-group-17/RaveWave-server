package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import ch.uzh.ifi.hase.soprafs22.entity.Player;

import java.util.ArrayList;

public class LeaderboardDTO {


    //Reihefolg bedütend i denne liste, evt. andere lösigsasatz nötig
    private ArrayList<Player> PlayerPositions;
    private ArrayList<Player> PrevPlayerPositions;
    private int[] LastScores;
    private int[] TotalScores;
    private int[] Streaks;
    //welle Datetyp?
    private ArrayList<String> ProfilePicture;

    public ArrayList<Player> getPlayerPositions() {
        return PlayerPositions;
    }

    public void setPlayerPositions(ArrayList<Player> playerPositions) {
        PlayerPositions = playerPositions;
    }

    public ArrayList<Player> getPrevPlayerPositions() {
        return PrevPlayerPositions;
    }

    public void setPrevPlayerPositions(ArrayList<Player> prevPlayerPositions) {
        PrevPlayerPositions = prevPlayerPositions;
    }

    public int[] getLastScores() {
        return LastScores;
    }

    public void setLastScore(int pos, int score) {
        LastScores[pos]= score;
    }

    public int[] getTotalScores() {
        return TotalScores;
    }

    public void setTotalScore(int pos, int totalScore) {
        TotalScores[pos] = totalScore;
    }

    public void setStreak(int pos, int streak) {
        Streaks[pos] = streak;
    }


    public ArrayList<String> getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(ArrayList<String> profilePicture) {
        ProfilePicture = profilePicture;
    }



}
