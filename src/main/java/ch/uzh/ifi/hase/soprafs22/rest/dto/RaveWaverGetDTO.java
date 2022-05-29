package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.time.LocalDate;

public class RaveWaverGetDTO {

    private Long id;
    private String username;
    private int level;

    private LocalDate creationDate;

    private String profilePicture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
