package com.Scheduled.Scheduled_server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class GameLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gameId;
    @Embedded
    private GameBase gameBase;

    public GameLibrary(GameBase gameBase) {
        this.gameBase = gameBase;
    }
}
