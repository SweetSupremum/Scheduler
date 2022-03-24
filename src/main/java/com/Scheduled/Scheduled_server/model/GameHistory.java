package com.Scheduled.Scheduled_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date schedulerTime;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "gameHistory")
    private List<Game> games;

    public GameHistory(Date schedulerTime) {
        this.schedulerTime = schedulerTime;
    }

}
