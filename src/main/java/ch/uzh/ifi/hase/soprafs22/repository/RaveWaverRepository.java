package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("RaveWaverRepository")
public interface RaveWaverRepository extends JpaRepository<RaveWaver, Long> {
    RaveWaver findByName(String name);

    RaveWaver findByUsername(String username);
}
