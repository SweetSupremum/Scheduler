package com.Scheduled.Scheduled_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString(exclude = "customer")
public class GameLibrary {

    @Id
    private String gameId;

    @ManyToOne
    private Customer customer;
}
