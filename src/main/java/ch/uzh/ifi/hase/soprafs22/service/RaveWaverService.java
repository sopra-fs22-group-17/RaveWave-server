package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.repository.RaveWaverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    public List<RaveWaver> getUsers() {
        return this.raveWaverRepository.findAll();
    }

    public RaveWaver createUser(RaveWaver newRaveWaver) {
        newRaveWaver.setToken(UUID.randomUUID().toString());

        checkIfUserExists(newRaveWaver);

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
    private void checkIfUserExists(RaveWaver raveWaverToBeCreated) {
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
}
