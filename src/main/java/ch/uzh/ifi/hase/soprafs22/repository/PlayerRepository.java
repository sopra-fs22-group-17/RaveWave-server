package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PlayerRepository")
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByPlayerNameAndLobbyId(String playerName, long lobbyId);

    List<Player> findByLobbyId(Long lobbyId);

    Player findById(long id);

    long deleteByLobbyId(long lobbyId);

}
