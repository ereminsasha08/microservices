package com.testtask.ms1.repository;

import com.testtask.ms1.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "select max(m.session_id) from message m", nativeQuery = true)
    Optional<Integer> findMaxSessionId();

    @Query(value = "select count(*) from message m where session_id = (select max(m.session_id) from message m)", nativeQuery = true)
    Optional<Integer> countMessagesFromLastSession();
}
