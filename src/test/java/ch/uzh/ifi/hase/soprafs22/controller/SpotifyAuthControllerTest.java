package ch.uzh.ifi.hase.soprafs22.controller;


import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyAuthCodeGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import ch.uzh.ifi.hase.soprafs22.service.SpotifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RestController
 * This is a WebMvcTest which allows to test the RESTController i.e. GET/POST/PUT request without actually sending them over the network.
 * This tests if the RESTController works.
 */

@WebMvcTest(SpotifyAuthController.class)
public class SpotifyAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpotifyService spotifyService;

    @MockBean
    private RaveWaverService raveWaverService;


    @Test
    void generateAuthorizationCodeUriTest() throws Exception{
        SpotifyAuthCodeGetDTO spotifyAuthCodeGetDTO = new SpotifyAuthCodeGetDTO();
        spotifyAuthCodeGetDTO.setRedirectionURL("https://accounts.spotify.com:443/authorize?client_id=d7d44473ad6a47cd86c580fcee015449&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2Fconnectspotify&scope=user-read-private%2Cplaylist-read-private%2Cuser-library-read%2Cuser-top-read%2Cuser-read-recently-played");

        given(spotifyService.authorizationCodeUri()).willReturn("https://accounts.spotify.com:443/authorize?client_id=d7d44473ad6a47cd86c580fcee015449&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2Fconnectspotify&scope=user-read-private%2Cplaylist-read-private%2Cuser-library-read%2Cuser-top-read%2Cuser-read-recently-played");

        // when
        MockHttpServletRequestBuilder getRequest = get("/Spotify/authorizationCodeUri").contentType(MediaType.APPLICATION_JSON);
        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.redirectionURL", is(spotifyAuthCodeGetDTO.getRedirectionURL())));
    }

    @Test
    void getAuthorizationCodeTest() throws Exception {
        SpotifyPostDTO spotifyPostDTO = new SpotifyPostDTO();
        spotifyPostDTO.setCode("ThisIsAnAuthorizationCode");

        String accessToken = "ThisIsAnAccessToken";

        //spotifyService.authorizationCode(spotifyPostDTO);
        given(spotifyService.getAccessToken()).willReturn(accessToken);

        MockHttpServletRequestBuilder postRequest = post("/Spotify/authorizationCode").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(spotifyPostDTO));;

        mockMvc.perform(postRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", is(accessToken)));
    }

    /**
     * Helper Method to convert Object into a JSON string such that the input can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     *
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The request body could not be created.%s", e.toString()));
        }
    }
}