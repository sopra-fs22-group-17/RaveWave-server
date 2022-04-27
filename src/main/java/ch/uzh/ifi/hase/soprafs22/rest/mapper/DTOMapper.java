package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.outgoing.QuestionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    RaveWaver convertRaveWaverPostDTOtoEntity(RaveWaverPostDTO raveWaverPostDTO);

    // TODO: Profile Picture
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "level", target = "level")
    @Mapping(source = "spotifyToken", target = "spotifyToken")
    @Mapping(source = "creationDate", target = "creationDate")
    RaveWaverGetDTO convertEntityToRaveWaverGetDTO(RaveWaver raveWaver);

    @Mapping(source = "username", target = "username")
    // @Mapping(source = "password", target = "password")
    @Mapping(source = "spotifyToken", target = "spotifyToken")
    RaveWaverPutDTO convertRaveWaverPutDTOtoEntity(RaveWaverPutDTO raveWaverPutDTO);

    @Mapping(source = "question", target = "question")
    @Mapping(source = "songID", target = "songID")
    @Mapping(source = "answers", target = "answers")
    QuestionDTO convertEntityToQuestionDTO(Question question);

    @Mapping(source = "playerName", target = "playerName")
    // @Mapping(source = "lobbyId", target = "lobbyId")
    Player convertPlayerPostDTOtoEntity(PlayerPostDTO playerPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "playerName", target = "playerName")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "streak", target = "streak")
    @Mapping(source = "correctAnswers", target = "correctAnswers")
    @Mapping(source = "lobbyId", target = "lobbyId")
    PlayerGetDTO convertEntityToPlayerGetDTO(Player player);

}
