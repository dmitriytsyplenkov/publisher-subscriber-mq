package com.dts.subscriber.model;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@ToString
public abstract class BaseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PK")
    protected Long idPk;
    @Column(name = "ID")
    protected Long id;
    @Column(name = "MSISDN")
    protected Long msisdn;
    @Column(name = "ACTION")
    protected ActionType action;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIMESTAMP")
    protected Date timestamp;

}
