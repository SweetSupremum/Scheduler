package com.Scheduled.Scheduled_server.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
public class Game {

    @Id
    private String id;

    private String name;
    private Double price;
    private String link;
    private String image;
    private Double discountPrice;
    private Integer discountPercent;

}
