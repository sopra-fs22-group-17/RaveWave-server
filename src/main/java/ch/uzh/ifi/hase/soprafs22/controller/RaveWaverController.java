package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.RaveWaverService;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RaveWaver Controller
 * This class is responsible for handling all REST request that are related to
 * the raveWaver.
 * The controller will receive the request and delegate the execution to the
 * RaveWaverService and finally return the result.
 */
@RestController
public class RaveWaverController {

    private final RaveWaverService raveWaverService;
    Logger log = LoggerFactory.getLogger(WebSocketController.class);

    RaveWaverController(RaveWaverService raveWaverService) {
        this.raveWaverService = raveWaverService;
    }

    @GetMapping("/ravewavers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<RaveWaverGetDTO> getAllRaveWaver() {
        // fetch all raveWavers in the internal representation
        List<RaveWaver> raveWavers = raveWaverService.getRaveWavers();
        List<RaveWaverGetDTO> raveWaverGetDTOS = new ArrayList<>();

        // convert each raveWaver to the API representation
        for (RaveWaver raveWaver : raveWavers) {
            raveWaverGetDTOS.add(DTOMapper.INSTANCE.convertEntityToRaveWaverGetDTO(raveWaver));
        }
        return raveWaverGetDTOS;
    }

    @GetMapping("/ravewavers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RaveWaverGetDTO getRaveWaver(@PathVariable Long id) {
        RaveWaver raveWaver = raveWaverService.getRaveWaverById(id);

        return DTOMapper.INSTANCE.convertEntityToRaveWaverGetDTO(raveWaver);
    }

    @PostMapping("/ravewavers")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public RaveWaverGetDTO createRaveWaver(@RequestBody RaveWaverPostDTO raveWaverPostDTO, HttpServletResponse response) throws IOException, ParseException, SpotifyWebApiException {
        // convert API raveWaver to internal representation
        RaveWaver raveWaverInput = DTOMapper.INSTANCE.convertRaveWaverPostDTOtoEntity(raveWaverPostDTO);

        // create raveWaver
        RaveWaver createdRaveWaver = raveWaverService.createRaveWaver(raveWaverInput);


        response.addHeader("Authorization", createdRaveWaver.getToken());

        // convert internal representation of raveWaver back to API
        RaveWaverGetDTO raveWaverGetDTO = DTOMapper.INSTANCE.convertEntityToRaveWaverGetDTO(createdRaveWaver);
        log.info("Ravewaver " + raveWaverGetDTO.getUsername() + " with ID " + raveWaverGetDTO.getId() + " created." );

        return raveWaverGetDTO;
    }

    @PostMapping("/ravewavers/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RaveWaverGetDTO loginRaveWaver(@RequestBody LoginPostDTO loginPostDTO, HttpServletResponse response) {
        RaveWaver raveWaver = raveWaverService.loginRaveWaver(loginPostDTO);

        response.addHeader("Authorization", raveWaver.getToken());

        return DTOMapper.INSTANCE.convertEntityToRaveWaverGetDTO(raveWaver);
    }

    @PutMapping("/ravewavers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public RaveWaverGetDTO updateRaveWaver(@RequestBody RaveWaverPutDTO raveWaverPutDTO, @PathVariable Long id) {

        RaveWaver raveWaverUpdate = raveWaverService.updateRaveWaver(raveWaverPutDTO, id);

        return DTOMapper.INSTANCE.convertEntityToRaveWaverGetDTO(raveWaverUpdate);
    }


}
