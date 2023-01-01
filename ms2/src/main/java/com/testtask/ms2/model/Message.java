package com.testtask.ms2.model;

import lombok.*;

import java.util.Date;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"sessionId", "mc1_timestamp", "mc2_timestamp"})
public class Message {

    private Integer id;

    private Integer sessionId;

    private Date mc1_timestamp;

    private Date mc2_timestamp;

    private Date mc3_timestamp;

    private Date end_timestamp;



}
