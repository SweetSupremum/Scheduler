package com.Scheduled.Scheduled_server.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "inLibrary")
@AllArgsConstructor
public class Game {
    @Id
    private String id;
    @Embedded
    private GameBase gameBase;
    private boolean inLibrary;

    public Game(String id, GameBase gameBase) {
        this.id = id;
        this.gameBase = gameBase;
    }

}
