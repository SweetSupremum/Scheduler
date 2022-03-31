package com.Scheduled.Scheduled_server.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "gameLibrary")
public class Game {
    @Id
    private String id;
    @Embedded
    private GameBase gameBase;
    @OneToMany(mappedBy = "game")
    private List<GameLibrary> gameLibrary;

    public Game(String id, GameBase gameBase) {
        this.id = id;
        this.gameBase = gameBase;
    }
}
