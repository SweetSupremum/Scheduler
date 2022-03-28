package com.Scheduled.Scheduled_server.repository;


import com.Scheduled.Scheduled_server.model.GameBase;
import com.Scheduled.Scheduled_server.model.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameHistoryRepository extends JpaRepository<GameHistory, GameBase> {
    GameHistory getFirstByIdLinkOrderByUpdatedAsc(String link);

}
