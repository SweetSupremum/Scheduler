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
@EqualsAndHashCode
@AllArgsConstructor
public class Game {
    @Id
    private String id;
    @Embedded
    private GameBase gameBase;


}
