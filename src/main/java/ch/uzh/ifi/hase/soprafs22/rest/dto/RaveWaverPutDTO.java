package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.security.NoSuchAlgorithmException;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import java.time.LocalDate;

public class UserPutDTO {

    private String username;

    private String password;

    private String spotifyToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = RaveWaverService.hashPasswordSHA256(password);
    }

    public String getSpotifyToken() {
        return spotifyToken;
    }

    public void setSpotifyToken(String spotifyToken) {
        this.spotifyToken = spotifyToken;
    }

}
