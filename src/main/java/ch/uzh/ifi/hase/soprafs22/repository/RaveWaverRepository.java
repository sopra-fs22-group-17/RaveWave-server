package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("raveWaverRepository")
public interface RaveWaverRepository extends JpaRepository<RaveWaver, Long> {
    RaveWaver findByUsername(String username);

    Optional<RaveWaver> findById(Long id);

    RaveWaver findByToken(String token);
}
