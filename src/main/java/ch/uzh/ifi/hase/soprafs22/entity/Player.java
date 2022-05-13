package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "PLAYER")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = true)
    private Long raveWaverId;

    @Column(nullable = false, unique = false)
    private String playerName;

    @Column(nullable = false)
    private long lobbyId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private int totalScore;

    @Column(nullable = false)
    private int roundScore;

    @Column(nullable = false)
    private int streak;

    @Column(nullable = false)
    private int correctAnswers;

    @Column(nullable = false)
    private String profilePicture;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRaveWaverId() {
        return raveWaverId;
    }

    public void setRaveWaverId(Long raveWaverId) {
        this.raveWaverId = raveWaverId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void addToScore(int newScore) {
        this.totalScore += newScore;
        this.roundScore = newScore;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int lastScore) {
        this.roundScore = lastScore;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public long getlobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long id) {
        this.lobbyId = id;
    }

    /**
     * public void roundResult(Answer answer, int playerScore, boolean
     * answerResult){
     * this.answers.add(answer);
     * this.totalScore += playerScore;
     *
     * if (answerResult) {
     * this.streak++;
     * this.correctAnswers++;
     * }
     *
     * }
     */
}
