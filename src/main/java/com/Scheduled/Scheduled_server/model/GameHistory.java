package com.Scheduled.Scheduled_server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.ZonedDateTime;


@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"created"})
public class GameHistory {
    @EmbeddedId
    private GameBase id;
    private ZonedDateTime created;


}
