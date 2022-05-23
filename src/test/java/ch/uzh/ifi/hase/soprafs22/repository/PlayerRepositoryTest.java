package ch.uzh.ifi.hase.soprafs22.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ch.uzh.ifi.hase.soprafs22.entity.Player;

@DataJpaTest
public class PlayerRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private PlayerRepository playerRepository;

  private Player player;

  @BeforeEach
  void setup() {
    player = new Player();
    player.setPlayerName("firstname@lastname");
    player.setLobbyId(1L);
    player.setToken("1");
    player.setTotalScore(420);
    player.setRoundScore(420);
    player.setStreak(3);
    player.setCorrectAnswers(3);

    entityManager.persist(player);
    entityManager.flush();

  }

  @Test
  public void findByNameSuccess() {
    // when
    Player found = playerRepository.findByPlayerNameAndLobbyId(player.getPlayerName(), player.getlobbyId());

    // then
    assertNotNull(found.getId());
    assertEquals(found.getPlayerName(), player.getPlayerName());
    assertEquals(found.getlobbyId(), player.getlobbyId());
    assertEquals(found.getToken(), player.getToken());
    assertEquals(found.getTotalScore(), player.getTotalScore());
    assertEquals(found.getRoundScore(), player.getRoundScore());
    assertEquals(found.getStreak(), player.getStreak());
    assertEquals(found.getCorrectAnswers(), player.getCorrectAnswers());
  }

  @Test
  public void findByLobbyIdSuccess() {
    List<Player> players = playerRepository.findByLobbyId(1L);

    assertEquals(players.get(0), player);

  }

  @Test
  public void findByLobbyIdNotInLobby() {
    List<Player> players = playerRepository.findByLobbyId(2L);

    assertTrue(players.isEmpty());

  }

  @Test
  public void findByIdTest() {
    Optional<Player> found = playerRepository.findById(player.getId());
    assertEquals(found.get(), player);
  }

}
