package com.Scheduled.Scheduled_server.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
@Data
public class GameHistory {

    @EmbeddedId
    private GameHistoryId gameHistoryId;

    private String name;
    private Double price;
    private String link;
    private String image;
    private Double discountPrice;
    private Integer discountPercent;
}
