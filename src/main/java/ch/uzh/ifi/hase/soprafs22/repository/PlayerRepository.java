package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("PlayerRepository")
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByPlayerName(String playerName);
}