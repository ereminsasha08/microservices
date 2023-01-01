package com.testtask.MS1.repository;

import com.testtask.MS1.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
