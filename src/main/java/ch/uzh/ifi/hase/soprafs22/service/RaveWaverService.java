package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LoginPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverPutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class RaveWaverService {

    private final Logger log = LoggerFactory.getLogger(RaveWaverService.class);

    private final RaveWaverRepository raveWaverRepository;

    @Autowired
    public RaveWaverService(@Qualifier("raveWaverRepository") RaveWaverRepository raveWaverRepository) {
        this.raveWaverRepository = raveWaverRepository;
    }

    public static void verifyPassword(String passwordRaveWaver, String passwordLogin) {
        if (!passwordRaveWaver.equals(passwordLogin)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is incorrect!");
        }
    }

    public static String hashPasswordSHA256(String password) throws NoSuchAlgorithmException {
        MessageDigest msgDgst = MessageDigest.getInstance("SHA-256");
        byte[] hash = msgDgst.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger no = new BigInteger(1, hash);
        StringBuilder passwordHash = new StringBuilder(no.toString(16));

        // Padding with tbe leading zeros
        while (passwordHash.length() < 32) {
            passwordHash.insert(0, '0');
        }
        return passwordHash.toString();
    }

    public List<RaveWaver> getRaveWavers() {
        return this.raveWaverRepository.findAll();
    }

    public RaveWaver createRaveWaver(RaveWaver newRaveWaver) {
        newRaveWaver.setToken(UUID.randomUUID().toString());
        newRaveWaver.setCreationDate(LocalDate.now());

        checkIfRaveWaverExists(newRaveWaver);

        // saves the given entity but data is only persisted in the database once
        // flush() is called
        newRaveWaver = raveWaverRepository.save(newRaveWaver);
        raveWaverRepository.flush();

        log.debug("Created Information for User: {}", newRaveWaver);
        return newRaveWaver;
    }

    /**
     * This is a helper method that will check the uniqueness criteria of the
     * username and the name
     * defined in the User entity. The method will do nothing if the input is unique
     * and throw an error otherwise.
     *
     * @param raveWaverToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see RaveWaver
     */
    private void checkIfRaveWaverExists(RaveWaver raveWaverToBeCreated) {
        RaveWaver raveWaverByUsername = raveWaverRepository.findByUsername(raveWaverToBeCreated.getUsername());

        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
        if (raveWaverByUsername != null && raveWaverByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "username and the name", "are"));
        }
        else if (raveWaverByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));
        }
        else if (raveWaverByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "name", "is"));
        }
    }

    public RaveWaver loginRaveWaver(LoginPostDTO loginPostDTO) {
        RaveWaver raveWaver = getUserByUsername(loginPostDTO.getUsername());

        RaveWaverService.verifyPassword(raveWaver.getPassword(), loginPostDTO.getPassword());
        // set user online
        return raveWaver;
    }

    public RaveWaver getUserByUsername(String username) {
        RaveWaver raveWaverByUsername = raveWaverRepository.findByUsername(username);
        if (raveWaverByUsername == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The username %s does not exist!", username));
        }
        return raveWaverByUsername;
    }

    public RaveWaver getRaveWaverById(Long id) {
        // Get user from repo by id
        Optional<RaveWaver> optionalRaveWaver = this.raveWaverRepository.findById(id);

        // check if the user exist
        checkIfIdExists(optionalRaveWaver, id);

        // return user
        return optionalRaveWaver.get();
    }

    private void checkIfIdExists(Optional<RaveWaver> userToBeUpdated, Long id) {
        if (!userToBeUpdated.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User with id:%d was not found!", id));
        }
    }

    public RaveWaver updateRaveWaver(RaveWaverPutDTO raveWaverPutDTO, Long id) {
        RaveWaver raveWaverToUpdate = getRaveWaverById(id);

        return raveWaverToUpdate;
    }
}
