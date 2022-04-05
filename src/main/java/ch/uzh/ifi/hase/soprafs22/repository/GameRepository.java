package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("GameRepository")
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByGameId(String playerName);
}