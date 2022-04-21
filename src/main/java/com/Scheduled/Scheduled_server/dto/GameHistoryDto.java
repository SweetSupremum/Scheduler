package com.Scheduled.Scheduled_server.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class GameHistoryDto {
    private ZonedDateTime created;
    private String gameId;
    private String name;
    private Double price;
    private String link;
    private String image;
    private Double discountPrice;
    private Integer discountPercent;
}
