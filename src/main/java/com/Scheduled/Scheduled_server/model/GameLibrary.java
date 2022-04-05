package com.Scheduled.Scheduled_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class GameLibrary {

    @Id
    private String gameId;

    @ManyToOne
    private Customer customer;
}
