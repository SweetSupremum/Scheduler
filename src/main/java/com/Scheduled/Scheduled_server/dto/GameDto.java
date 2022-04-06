package com.Scheduled.Scheduled_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDto {

    private String id;

    private String name;
    private Double price;
    private String link;
    private String image;
    private Double discountPrice;
    private Integer discountPercent;

    private boolean inLibrary;

}
