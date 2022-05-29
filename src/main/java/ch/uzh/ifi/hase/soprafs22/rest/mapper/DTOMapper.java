package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * * transform/map the internal representation
 * * of an entity (e.g., the User) to the external/API representation (e.g.,
 * * UserGetDTO for getting, UserPostDTO for creating)
 * * and vice versa.
 * * Additional mappers can be defined for new entities.
 * * Always created one mapper for getting information (GET) and one mapper for
 * * creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    RaveWaver convertRaveWaverPostDTOtoEntity(RaveWaverPostDTO raveWaverPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "level", target = "level")
    //@Mapping(source = "spotifyToken", target = "spotifyToken")
    //@Mapping(source = "spotifyRefreshToken", target = "spotifyRefreshToken")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "profilePicture", target = "profilePicture")
    RaveWaverGetDTO convertEntityToRaveWaverGetDTO(RaveWaver raveWaver);


    @Mapping(source = "playerName", target = "playerName")
    @Mapping(source = "raveWaverId", target = "raveWaverId")
    Player convertPlayerPostDTOtoEntity(PlayerPostDTO playerPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "playerName", target = "playerName")
    @Mapping(source = "roundScore", target = "roundScore")
    @Mapping(source = "totalScore", target = "totalScore")
    @Mapping(source = "streak", target = "streak")
    @Mapping(source = "correctAnswers", target = "correctAnswers")
    @Mapping(source = "lobbyId", target = "lobbyId")
    @Mapping(source = "profilePicture", target = "profilePicture")
    PlayerGetDTO convertEntityToPlayerGetDTO(Player player);


}
