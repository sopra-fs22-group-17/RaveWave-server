package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.security.NoSuchAlgorithmException;

import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;

public class LoginPostDTO {
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = RaveWaverService.hashPasswordSHA256(password);
    }

}
