package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

import ch.uzh.ifi.hase.soprafs22.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardDTO {


    //Reihefolg bedütend i denne liste, evt. andere lösigsasatz nötig
    private List<Player> PlayerPositions;
    private List<Player> PrevPlayerPositions;
    private ArrayList<Integer> LastScores;
    private ArrayList<Integer> TotalScores;
    private ArrayList<Integer> Streaks;
    //welle Datetyp?
    private ArrayList<String> ProfilePicture;

    public LeaderboardDTO(){
        this.LastScores = new ArrayList<Integer>();
        this.TotalScores = new ArrayList<Integer>();
        this.Streaks = new ArrayList<Integer>();

        for(int i=0; i<20; i++){
            LastScores.add(0);
            TotalScores.add(0);
            Streaks.add(0);
        }
    }

    public List<Player> getPlayerPositions() {
        return PlayerPositions;
    }

    public void setPlayerPositions(List<Player> playerPositions) {
        PlayerPositions = playerPositions;
    }

    public List<Player> getPrevPlayerPositions() {
        return PrevPlayerPositions;
    }

    public void setPrevPlayerPositions(List<Player> prevPlayerPositions) {
        PrevPlayerPositions = prevPlayerPositions;
    }

    public ArrayList<Integer> getLastScores() {
        return LastScores;
    }

    public void setLastScore(int pos, int score) {
        LastScores.set(pos, score);
    }

    public ArrayList<Integer> getTotalScores() {
        return TotalScores;
    }

    public void setTotalScore(int pos, Integer totalScore) {
        TotalScores.set(pos, totalScore);
    }

    public void setStreak(int pos, Integer streak) {
        Streaks.set(pos, streak);
    }


    public ArrayList<String> getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(ArrayList<String> profilePicture) {
        ProfilePicture = profilePicture;
    }



}
