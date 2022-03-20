package com.Scheduled.Scheduled_server.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Game extends Auditable {
    @Id
    private String id;
    private String name;
    private Double price;
    private String link;
    private String image;
    private Double discountPrice;
    private Integer discountPercent;

    public Game(String id, String name, Double price, String link, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.link = link;
        this.image = image;
    }
}
