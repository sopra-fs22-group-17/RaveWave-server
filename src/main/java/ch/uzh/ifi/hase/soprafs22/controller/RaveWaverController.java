package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class RaveWaverController {

    private final RaveWaverService raveWaverService;

    RaveWaverController(RaveWaverService raveWaverService) {
        this.raveWaverService = raveWaverService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<RaveWaverGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<RaveWaver> raveWavers = raveWaverService.getUsers();
        List<RaveWaverGetDTO> raveWaverGetDTOS = new ArrayList<>();

        // convert each user to the API representation
        for (RaveWaver raveWaver : raveWavers) {
            raveWaverGetDTOS.add(DTOMapper.INSTANCE.convertEntityToRaveWaverGetDTO(raveWaver));
        }
        return raveWaverGetDTOS;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public RaveWaverGetDTO createUser(@RequestBody RaveWaverPostDTO raveWaverPostDTO) {
        // convert API user to internal representation
        RaveWaver raveWaverInput = DTOMapper.INSTANCE.convertRaveWaverPostDTOtoEntity(raveWaverPostDTO);

        // create user
        RaveWaver createdRaveWaver = raveWaverService.createUser(raveWaverInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToRaveWaverGetDTO(createdRaveWaver);
    }
}
