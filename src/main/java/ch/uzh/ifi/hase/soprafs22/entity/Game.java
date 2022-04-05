package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "GAME")
public class Game {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long gameId;

}
