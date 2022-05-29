package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LoginPostDTO;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RaveWaverServiceTest {

    @Mock
    private RaveWaverRepository raveWaverRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private RaveWaverService raveWaverService;

    @Mock
    private RaveWaver testRaveWaver;

    @InjectMocks
    private GameService gameService;

    @MockBean
    private SpotifyService spotifyService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testRaveWaver = new RaveWaver();
        testRaveWaver.setId(1L);
        testRaveWaver.setUsername("testName");
        testRaveWaver.setToken("token");
        testRaveWaver.setPassword("passwrd");

        // when -> any object is being save in the raveWaverRepository -> return the
        // dummy
        // testRaveWaver
        when(raveWaverRepository.save(Mockito.any())).thenReturn(testRaveWaver);
    }

    @Test
    public void createRaveWaverValidInputSuccess() throws IOException, ParseException, SpotifyWebApiException {
        // when -> any object is being save in the raveWaverRepository -> return the
        // dummy
        // testRaveWaver
        RaveWaver createdRaveWaver = raveWaverService.createRaveWaver(testRaveWaver);

        // then
        Mockito.verify(raveWaverRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testRaveWaver.getId(), createdRaveWaver.getId());
        assertEquals(testRaveWaver.getUsername(), createdRaveWaver.getUsername());
        assertEquals(testRaveWaver.getPassword(), createdRaveWaver.getPassword());
        assertNotNull(createdRaveWaver.getToken());
        assertNotNull(createdRaveWaver.getCreationDate());
    }

    @Test
    public void createRaveWaverDuplicateNameThrowsException() throws IOException, ParseException, SpotifyWebApiException {
        // given -> a first raveWaver has already been created
        raveWaverService.createRaveWaver(testRaveWaver);

        // when -> setup additional mocks for RaveWaverRepository
        when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);

        // then -> attempt to create second raveWaver with same raveWaver -> check that
        // an error
        // is thrown
        assertThrows(ResponseStatusException.class, () -> raveWaverService.createRaveWaver(testRaveWaver));
    }

    @Test
    public void createRaveWaverDuplicateInputsThrowsException() throws IOException, ParseException, SpotifyWebApiException {
        // given -> a first raveWaver has already been created
        raveWaverService.createRaveWaver(testRaveWaver);

        // when -> setup additional mocks for RaveWaverRepository
        when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);

        // then -> attempt to create second raveWaver with same raveWaver -> check that
        // an error
        // is thrown
        assertThrows(ResponseStatusException.class, () -> raveWaverService.createRaveWaver(testRaveWaver));
    }

    @Test
    public void hashPasswordSHA256Test() throws NoSuchAlgorithmException {
        String hash = "a13a5d052e556b73d9bd945a696dfe95a679bf13c2668c8d85cdda123b107857";
        assertEquals(RaveWaverService.hashPasswordSHA256(testRaveWaver.getPassword()), hash);
    }

    @Test
    public void loginRaveWaverSuccessTest() throws NoSuchAlgorithmException, IOException, ParseException, SpotifyWebApiException {
        LoginPostDTO loginPostDTO = new LoginPostDTO();
        loginPostDTO.setUsername(testRaveWaver.getUsername());
        loginPostDTO.setPassword(testRaveWaver.getPassword());
        testRaveWaver.setPassword(RaveWaverService.hashPasswordSHA256(testRaveWaver.getPassword()));
        raveWaverService.createRaveWaver(testRaveWaver);
        when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);
        RaveWaver loginRaveWaver = raveWaverService.loginRaveWaver(loginPostDTO);

        assertEquals(testRaveWaver, loginRaveWaver);

    }

    @Test
    public void loginRaveWaverInvalidPaswordTest() throws NoSuchAlgorithmException, IOException, ParseException, SpotifyWebApiException {
        LoginPostDTO loginPostDTO = new LoginPostDTO();
        loginPostDTO.setUsername(testRaveWaver.getUsername());
        loginPostDTO.setPassword("wrong password");
        testRaveWaver.setPassword(RaveWaverService.hashPasswordSHA256(testRaveWaver.getPassword()));
        raveWaverService.createRaveWaver(testRaveWaver);
        when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);
        assertThrows(ResponseStatusException.class, () -> raveWaverService.loginRaveWaver(loginPostDTO));
    }

    @Test
    public void loginRaveWaverUserDoesNotExistTest() throws NoSuchAlgorithmException {
        LoginPostDTO loginPostDTO = new LoginPostDTO();
        loginPostDTO.setUsername(testRaveWaver.getUsername());
        loginPostDTO.setPassword("wrong password");
        testRaveWaver.setPassword(RaveWaverService.hashPasswordSHA256(testRaveWaver.getPassword()));
        when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);
        assertThrows(ResponseStatusException.class, () -> raveWaverService.loginRaveWaver(loginPostDTO));
    }

    @Test
    public void getUserByUsernameNotExistingTest() {
        assertThrows(ResponseStatusException.class, () -> raveWaverService.getUserByUsername("doesn't exist"));
    }

    @Test
    public void getRaveWaverByIdNotExistingTest() {
        assertThrows(ResponseStatusException.class, () -> raveWaverService.getRaveWaverById(2L));
    }

    @Test
    public void addRaveWaverToLobbyTest() {
        Game game = org.mockito.Mockito.mock(Game.class);
        gameService.createNewLobby(spotifyService);
        GameRepository.addGame(1, game);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "token");
        Mockito.when(raveWaverRepository.findByToken("token")).thenReturn(testRaveWaver);

        Player player = new Player();
        player.setPlayerName("[RW] testName");
        player.setRaveWaverId(1L);
        player.setLobbyId(1L);
        player.setToken("playerToken");
        when(playerRepository.save(Mockito.any())).thenReturn(player);

        Player actual = raveWaverService.addRaveWaverToLobby(mockRequest, 1L);
        assertEquals(player.getPlayerName(), actual.getPlayerName());
    }

    @Test
    public void addRaveWaverToLobbyInvalidTest() {
        Game game = org.mockito.Mockito.mock(Game.class);
        gameService.createNewLobby(spotifyService);
        GameRepository.addGame(1, game);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "token");
        Mockito.when(raveWaverRepository.findByToken("token")).thenReturn(testRaveWaver);

        Player player = new Player();
        player.setPlayerName("[RW] testName");
        player.setRaveWaverId(1L);
        player.setLobbyId(1L);
        player.setToken("playerToken");
        when(playerRepository.save(Mockito.any())).thenReturn(player);

        Player actual = raveWaverService.addRaveWaverToLobby(mockRequest, 1L);
        assertEquals(player.getPlayerName(), actual.getPlayerName());
    }

    //TODO
    @Test
    public void getRaveWaverByTokenTest() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "token");
        Mockito.when(raveWaverRepository.findByToken("token")).thenReturn(testRaveWaver);

        raveWaverService.getRaveWaverByToken(mockRequest);
    }

    @Test
    public void getRaveWaverByInvalidTokenTest() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "invalidToken");

        assertThrows(ResponseStatusException.class, () -> raveWaverService.getRaveWaverByToken(mockRequest));
    }

    @Disabled
    @Test
    public void updateToken() throws IOException, ParseException, SpotifyWebApiException {
        SpotifyService spotifyService = org.mockito.Mockito.mock(SpotifyService.class);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "token");
        Mockito.when(raveWaverRepository.findByToken("token")).thenReturn(testRaveWaver);

        when(spotifyService.getAccessToken()).thenReturn("newToken");
        when(spotifyService.getRefreshToken()).thenReturn("refreshToken");
        when(spotifyService.generateProfilePicture(testRaveWaver)).thenReturn("profilePicture");

        raveWaverService.updateSpotifyToken(mockRequest, spotifyService);
        assertEquals("newToken", testRaveWaver.getToken());
    }
}