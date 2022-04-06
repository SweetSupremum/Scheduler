package com.Scheduled.Scheduled_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameHistoryId implements Serializable {

    private ZonedDateTime created;
    private String gameId;

}
