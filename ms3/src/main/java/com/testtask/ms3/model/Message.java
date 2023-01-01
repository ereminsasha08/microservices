package com.testtask.ms3.model;

import lombok.*;

import java.util.Date;




@Getter
@Setter
@ToString(of = {"sessionId", "mc1_timestamp", "mc2_timestamp", "mc3_timestamp"})
public class Message {

    private Integer id;

    private Integer sessionId;

    private Date mc1_timestamp;

    private Date mc2_timestamp;

    private Date mc3_timestamp;

    private Date end_timestamp;



}
