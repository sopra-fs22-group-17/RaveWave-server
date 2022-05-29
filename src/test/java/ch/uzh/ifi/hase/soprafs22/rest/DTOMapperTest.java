package ch.uzh.ifi.hase.soprafs22.rest;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DTOMapperTest {
    @Test
    void ConvertRaveWaverPostDTOToEntityTest() throws NoSuchAlgorithmException {

        RaveWaverPostDTO raveWaverPostDTO = new RaveWaverPostDTO();
        raveWaverPostDTO.setUsername("username");
        raveWaverPostDTO.setPassword("1235");

        // MAP -> Create user
        RaveWaver raveWaver = DTOMapper.INSTANCE.convertRaveWaverPostDTOtoEntity(raveWaverPostDTO);
        // check content
        assertEquals(raveWaverPostDTO.getUsername(), raveWaver.getUsername());
        assertEquals(raveWaverPostDTO.getPassword(), raveWaver.getPassword());
    }

    @Test
    void convertEntitytoRaveWaverGetDTOTest() throws NoSuchAlgorithmException {
        RaveWaver raveWaver = new RaveWaver();
        raveWaver.setId(1L);
        raveWaver.setUsername("test");
        raveWaver.setToken("token");
        raveWaver.setLevel(69);
        raveWaver.setSpotifyToken("spotifyToken");
        raveWaver.setCreationDate(LocalDate.now());

        RaveWaverGetDTO raveWaverGetDTO = DTOMapper.INSTANCE.convertEntityToRaveWaverGetDTO(raveWaver);

        assertEquals(raveWaver.getId(), raveWaverGetDTO.getId());
        assertEquals(raveWaver.getUsername(), raveWaverGetDTO.getUsername());
        assertEquals(raveWaver.getLevel(), raveWaverGetDTO.getLevel());
        assertEquals(raveWaver.getCreationDate(), raveWaverGetDTO.getCreationDate());

    }

    @Test
    void conevertPlayerPostDTOtoEntityTest() {
        PlayerPostDTO playerPostDTO = new PlayerPostDTO();

        Player testPlayer = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);

        assertEquals(playerPostDTO.getPlayerName(), testPlayer.getPlayerName());
    }

    @Test
    void convertEntityToPlayerGetDTOTest() {
        Player player = new Player();
        player.setId(1L);
        player.setPlayerName("playerName");
        player.setToken("token");
        player.setRoundScore(420);
        player.setTotalScore(69);
        player.setStreak(3);
        player.setCorrectAnswers(3);
        player.setLobbyId(1L);

        PlayerGetDTO playerGetDTO = DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(player);

        assertEquals(playerGetDTO.getId(), player.getId());
        assertEquals(playerGetDTO.getPlayerName(), player.getPlayerName());
        assertEquals(playerGetDTO.getRoundScore(), player.getRoundScore());
        assertEquals(playerGetDTO.getTotalScore(), player.getTotalScore());
        assertEquals(playerGetDTO.getStreak(), player.getStreak());
        assertEquals(playerGetDTO.getCorrectAnswers(), player.getCorrectAnswers());
        assertEquals(playerGetDTO.getLobbyId(), player.getlobbyId());

    }
}
