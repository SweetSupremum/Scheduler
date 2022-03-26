package com.Scheduled.Scheduled_server.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
public class GameBase implements Serializable {

    private String name;
    private Double price;
    private String link;
    private String image;
    private Double discountPrice;
    private Integer discountPercent;

    public GameBase(String name, Double price, String link, String image) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.image = image;
    }


}
