package com.Scheduled.Scheduled_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private String name;
    private Double price;
    private Double discountPrice;
    private String link;
    private String image;
    private Integer discountPercent;
}
