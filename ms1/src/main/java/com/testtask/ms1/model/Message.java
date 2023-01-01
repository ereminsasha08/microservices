package com.testtask.ms1.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "message")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer sessionId;

    private Date mc1_timestamp;

    private Date mc2_timestamp;

    private Date mc3_timestamp;

    private Date end_timestamp;


}
