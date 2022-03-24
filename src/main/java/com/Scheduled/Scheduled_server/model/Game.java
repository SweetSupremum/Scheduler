package com.Scheduled.Scheduled_server.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"created", "gameHistory"})
public class Game extends Auditable {
    private Date created;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GameHistory gameHistory;

    public Game(String id, String name, Double price, String link, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.link = link;
        this.image = image;
    }
}
