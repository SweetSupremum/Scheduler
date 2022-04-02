package com.Scheduled.Scheduled_server.repository;


import com.Scheduled.Scheduled_server.model.GameHistoryId;
import com.Scheduled.Scheduled_server.model.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameHistoryRepository extends JpaRepository<GameHistory, GameHistoryId> {

}
