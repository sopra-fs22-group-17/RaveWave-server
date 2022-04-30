package ch.uzh.ifi.hase.soprafs22.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LoginPostDTO;

public class RaveWaverServiceTest {

    @Mock
    private RaveWaverRepository raveWaverRepository;

    @InjectMocks
    private RaveWaverService raveWaverService;

    private RaveWaver testRaveWaver;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testRaveWaver = new RaveWaver();
        testRaveWaver.setId(1L);
        testRaveWaver.setUsername("testName");
        testRaveWaver.setPassword("passwrd");

        // when -> any object is being save in the raveWaverRepository -> return the
        // dummy
        // testRaveWaver
        Mockito.when(raveWaverRepository.save(Mockito.any())).thenReturn(testRaveWaver);
    }

    @Test
    public void createRaveWaver_validInputs_success() {
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
    public void createRaveWaver_duplicateName_throwsException() {
        // given -> a first raveWaver has already been created
        raveWaverService.createRaveWaver(testRaveWaver);

        // when -> setup additional mocks for RaveWaverRepository
        Mockito.when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);

        // then -> attempt to create second raveWaver with same raveWaver -> check that
        // an error
        // is thrown
        assertThrows(ResponseStatusException.class, () -> raveWaverService.createRaveWaver(testRaveWaver));
    }

    @Test
    public void createRaveWaver_duplicateInputs_throwsException() {
        // given -> a first raveWaver has already been created
        raveWaverService.createRaveWaver(testRaveWaver);

        // when -> setup additional mocks for RaveWaverRepository
        Mockito.when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);

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
    public void loginRaveWaverSuccessTest() throws NoSuchAlgorithmException {
        LoginPostDTO loginPostDTO = new LoginPostDTO();
        loginPostDTO.setUsername(testRaveWaver.getUsername());
        loginPostDTO.setPassword(testRaveWaver.getPassword());
        testRaveWaver.setPassword(RaveWaverService.hashPasswordSHA256(testRaveWaver.getPassword()));
        raveWaverService.createRaveWaver(testRaveWaver);
        Mockito.when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);
        RaveWaver loginRaveWaver = raveWaverService.loginRaveWaver(loginPostDTO);

        assertEquals(testRaveWaver, loginRaveWaver);

    }

    @Test
    public void loginRaveWaverInvalidPaswordTest() throws NoSuchAlgorithmException {
        LoginPostDTO loginPostDTO = new LoginPostDTO();
        loginPostDTO.setUsername(testRaveWaver.getUsername());
        loginPostDTO.setPassword("wrong password");
        testRaveWaver.setPassword(RaveWaverService.hashPasswordSHA256(testRaveWaver.getPassword()));
        raveWaverService.createRaveWaver(testRaveWaver);
        Mockito.when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);
        assertThrows(ResponseStatusException.class, () -> raveWaverService.loginRaveWaver(loginPostDTO));
    }

    @Test
    public void loginRaveWaverUserDoesNotExistTest() throws NoSuchAlgorithmException {
        LoginPostDTO loginPostDTO = new LoginPostDTO();
        loginPostDTO.setUsername(testRaveWaver.getUsername());
        loginPostDTO.setPassword("wrong password");
        testRaveWaver.setPassword(RaveWaverService.hashPasswordSHA256(testRaveWaver.getPassword()));
        Mockito.when(raveWaverRepository.findByUsername(Mockito.any())).thenReturn(testRaveWaver);
        assertThrows(ResponseStatusException.class, () -> raveWaverService.loginRaveWaver(loginPostDTO));
    }

}