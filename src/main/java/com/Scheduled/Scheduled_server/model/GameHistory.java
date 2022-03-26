package com.Scheduled.Scheduled_server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;


@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"created", "updated"})
public class GameHistory {
    @EmbeddedId
    private GameBase id;
    private Date created;
    private Date updated;

}
