package com.Scheduled.Scheduled_server.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date created;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    protected Date updated;
}
