package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.RaveWaver;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class RaveWaverRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RaveWaverRepository raveWaverRepository;

    private RaveWaver raveWaver;


    @BeforeEach
    public void setup() {
        raveWaver = new RaveWaver();
        raveWaver.setUsername("firstname@lastname");
        raveWaver.setPassword("password");
        raveWaver.setToken("token");
        raveWaver.setLevel(3);
        raveWaver.setCreationDate(LocalDate.now());

        entityManager.persist(raveWaver);
        entityManager.flush();

    }

    @Test
    public void findByUsernameTest() {
        RaveWaver found = raveWaverRepository.findByUsername(raveWaver.getUsername());

        assertNotNull(found.getId());
        assertEquals(raveWaver.getUsername(), found.getUsername());
        assertEquals(raveWaver.getPassword(), found.getPassword());
        assertEquals(raveWaver.getToken(), found.getToken());
        assertEquals(raveWaver.getLevel(), found.getLevel());
        assertEquals(raveWaver.getCreationDate(), found.getCreationDate());


    }


    @Disabled
    @Test
    public void findByIdTest() {
        Optional<RaveWaver> optFound = raveWaverRepository.findById(1L);
        RaveWaver found = optFound.get();
        assertNotNull(found.getId());
        assertEquals(raveWaver.getUsername(), found.getUsername());
        assertEquals(raveWaver.getPassword(), found.getPassword());
        assertEquals(raveWaver.getToken(), found.getToken());
        assertEquals(raveWaver.getLevel(), found.getLevel());
        assertEquals(raveWaver.getCreationDate(), found.getCreationDate());
    }

    @AfterEach
    public void tearDown(){
        raveWaverRepository.deleteAll();
    }

}

