package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Question;
import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.RaveWaverPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SpotifyGetDTO;
import ch.uzh.ifi.hase.soprafs22.websockets.dto.QuestionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "username", target = "username")
    RaveWaver convertRaveWaverPostDTOtoEntity(RaveWaverPostDTO raveWaverPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    RaveWaverGetDTO convertEntityToRaveWaverGetDTO(RaveWaver raveWaver);

    @Mapping(source = "question", target = "question")
    @Mapping(source = "songID", target = "songID")
    @Mapping(source = "answers", target = "answers")
    QuestionDTO convertEntityToQuestionDTO(Question question);


}
