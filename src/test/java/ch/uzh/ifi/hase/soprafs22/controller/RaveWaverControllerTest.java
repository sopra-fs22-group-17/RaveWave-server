package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LoginPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RaveWaverControllerTest
 * This is a WebMvcTest which allows to test the RaveWaverController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the RaveWaverController works.
 */

@WebMvcTest(RaveWaverController.class)
public class RaveWaverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private RaveWaver raveWaver;

    @MockBean
    private RaveWaverService raveWaverService;

    @BeforeEach
    public void testSetup() {
        raveWaver = new RaveWaver();
        raveWaver.setId(1L);
        raveWaver.setUsername("raveWavername");
        raveWaver.setPassword("password");
        raveWaver.setToken("1");
        raveWaver.setLevel(2);
        raveWaver.setSpotifyToken("spotifyToken");
        raveWaver.setCreationDate(LocalDate.now());
    }

    @Test
    public void test_get_allRaveWavers() throws Exception {

        List<RaveWaver> allRaveWavers = Collections.singletonList(raveWaver);

        // this mocks the RaveWaverService -> we define above what the raveWaverService should
        // return when getRaveWavers() is called
        given(raveWaverService.getRaveWavers()).willReturn(allRaveWavers);

        // when
        MockHttpServletRequestBuilder getRequest = get("/ravewavers").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(raveWaver.getId().intValue())))
                .andExpect(jsonPath("$[0].username", is(raveWaver.getUsername())))
                .andExpect(jsonPath("$[0].creationDate", is(raveWaver.getCreationDate().toString())))
                .andExpect(jsonPath("$[0].level", is(raveWaver.getLevel())));
    }

    @Test
    public void test_post_createNewRaveWaver() throws Exception {
        // given
        RaveWaverPostDTO raveWaverPostDTO = new RaveWaverPostDTO();
        raveWaverPostDTO.setUsername("testUsername");
        raveWaverPostDTO.setPassword("password");

        given(raveWaverService.createRaveWaver(Mockito.any())).willReturn(raveWaver);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/ravewavers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(raveWaverPostDTO));

        // then
        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(raveWaver.getId().intValue())))
                .andExpect(jsonPath("$.username", is(raveWaver.getUsername())))
                .andExpect(jsonPath("$.creationDate", is(raveWaver.getCreationDate().toString())))
                .andExpect(jsonPath("$.level", is(raveWaver.getLevel())))
                .andExpect(MockMvcResultMatchers.header()
                        .stringValues("Authorization", "1"));
        //          .andExpect(jsonPath("$.spotifyToken", is(raveWaver.getSpotifyToken())))
//        .andExpect(jsonPath("$.token", is(raveWaver.getToken())));
    }

    @Test
    public void test_post_createNewRaveWaver_raveWavernameConflict() throws Exception {
        given(raveWaverService.createRaveWaver(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.CONFLICT, "error"));

        RaveWaverPostDTO raveWaverPostDTO = new RaveWaverPostDTO();
        raveWaverPostDTO.setUsername("raveWavername");
        raveWaverPostDTO.setPassword("password");

        MockHttpServletRequestBuilder postRequest = post("/ravewavers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(raveWaverPostDTO));

        mockMvc.perform(postRequest)
                .andExpect(status().isConflict());
    }

    @Test
    public void test_post_raveWaverLogin() throws Exception {

        LoginPostDTO loginPostDTO = new LoginPostDTO();
        loginPostDTO.setUsername("raveWavername");
        loginPostDTO.setPassword("password");

        given(raveWaverService.loginRaveWaver(Mockito.any())).willReturn(raveWaver);

        MockHttpServletRequestBuilder postRequest = post("/ravewavers/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginPostDTO));

        mockMvc.perform(postRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(raveWaver.getId().intValue())))
                .andExpect(jsonPath("$.username", is(raveWaver.getUsername())))
                .andExpect(jsonPath("$.creationDate", is(raveWaver.getCreationDate().toString())))
                .andExpect(jsonPath("$.level", is(raveWaver.getLevel())));
        //TODO TEST token

    }

    @Test
    public void test_get_singleRaveWaver() throws Exception {

        long id = raveWaver.getId();

        given(raveWaverService.getRaveWaverById(id)).willReturn(raveWaver);

        MockHttpServletRequestBuilder getRequest = get("/ravewavers/" + id).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(raveWaver.getId().intValue())))
                .andExpect(jsonPath("$.username", is(raveWaver.getUsername())))
                .andExpect(jsonPath("$.creationDate", is(raveWaver.getCreationDate().toString())))
                .andExpect(jsonPath("$.level", is(raveWaver.getLevel())));

    }

    @Test
    public void test_get_singleRaveWaver_raveWaverNotFound() throws Exception {

        long id = 0;
        given(raveWaverService.getRaveWaverById(id)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "error"));

        MockHttpServletRequestBuilder getRequest = get("/raveWavers/" + id).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isNotFound());
    }

    /**
     * Helper Method to convert raveWaverPostDTO into a JSON string such that the input
     * can be processed
     * Input will look like this: {"name": "Test RaveWaver", "raveWavername": "testUsername"}
     *
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e));
        }
    }
}
