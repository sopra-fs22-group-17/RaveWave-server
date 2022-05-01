package ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing;

public class LeaderboardEntry {
    private Long playerId;

    private String playerName;

    private int playerPosition;
    //private int prevPosition;
    private int roundScore;
    private int totalScore;
    private int streak;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    /*
        public int getPrevPosition() {
            return prevPosition;
        }

        public void setPrevPosition(int prevPosition) {
            this.prevPosition = prevPosition;
        }
    */
    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


}
