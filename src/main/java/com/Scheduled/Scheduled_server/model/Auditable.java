package com.Scheduled.Scheduled_server.model;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class Auditable {
    @Id
    protected String id;
    protected String name;
    protected Double price;
    protected String link;
    protected String image;
    protected Double discountPrice;
    protected Integer discountPercent;
}
