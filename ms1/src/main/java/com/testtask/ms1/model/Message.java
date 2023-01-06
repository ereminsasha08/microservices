package com.testtask.ms1.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "message", indexes = @Index(columnList = "session_id"))
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "session_id")
    private Integer sessionId;

    private Date mc1_timestamp;

    private Date mc2_timestamp;

    private Date mc3_timestamp;

    private Date end_timestamp;

    public Message(Integer sessionId, Date mc1_timestamp) {
        this.sessionId = sessionId;
        this.mc1_timestamp = mc1_timestamp;
    }

    @PrePersist
    private void endTime() {
        end_timestamp = new Date();
    }
}
