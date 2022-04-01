package com.Scheduled.Scheduled_server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class GameLibrary {
    @Id
    private String gameId;
    @Embedded
    private GameBase gameBase;

    public GameLibrary(GameBase gameBase) {
        this.gameBase = gameBase;
    }
}
