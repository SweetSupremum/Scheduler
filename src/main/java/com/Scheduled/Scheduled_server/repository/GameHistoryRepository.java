package com.Scheduled.Scheduled_server.repository;


import com.Scheduled.Scheduled_server.model.GameBase;
import com.Scheduled.Scheduled_server.model.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    boolean existsByGameBaseLink(String link);

    Optional<GameHistory> findByGameBaseLink(String link);

    GameHistory getByGameBase(GameBase gameBase);
}
