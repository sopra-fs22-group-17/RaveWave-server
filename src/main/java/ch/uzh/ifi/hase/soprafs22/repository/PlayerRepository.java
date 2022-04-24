package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PlayerRepository")
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByPlayerName(String playerName);
    Player findByPlayerId(Long id);
	List<Player> findByLobbyId(Long lobbyid);
}
