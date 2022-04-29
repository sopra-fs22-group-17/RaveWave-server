package ch.uzh.ifi.hase.soprafs22.rest.dto;

public class SpotifyAuthCodeGetDTO {
    private String RedirectionURL;

    public String getRedirectionURL() {
        return RedirectionURL;
    }

    public void setRedirectionURL(String redirectionURL) {
        RedirectionURL = redirectionURL;
    }
}
