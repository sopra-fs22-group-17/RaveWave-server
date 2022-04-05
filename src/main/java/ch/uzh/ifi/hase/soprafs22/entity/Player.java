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
    @Column(nullable = false)
    private Long id;

    @Column(nullable = true)
    private Long raveWaverId;

    @Column(nullable = false, unique = true)
    private String playerName;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int streak;

    @Column(nullable = false)
    private int correctAnswers;
/*
    @Column(nullable = false)
    @Type(type = "ch.uzh.ifi.hase.soprafs22.entity.Answer")
    private List<Answer> answers;
*/

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
/*
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }*/
}
