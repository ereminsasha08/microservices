package com.testtask.ms1.repository;

import com.testtask.ms1.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "select max(m.session_id) from message m", nativeQuery = true)
    Optional<Integer> findMaxSessionId();

    @Query(value = "select * from message m2 where id in " +
            "((select max(id) from message m where session_id = (select max(m.session_id) from message m)), " +
            "(select min(id) from message m where session_id = (select max(m.session_id) from message m)))", nativeQuery = true)
    List<Message> findFirstAndLastMessageFromLastSession();
}
