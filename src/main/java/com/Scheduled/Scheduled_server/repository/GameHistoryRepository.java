package com.Scheduled.Scheduled_server.repository;


import com.Scheduled.Scheduled_server.model.GameHistory;
import com.Scheduled.Scheduled_server.model.GameHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface GameHistoryRepository extends JpaRepository<GameHistory, GameHistoryId> {
    Optional<List<GameHistory>> findAllByGameHistoryId_GameIdOrderByGameHistoryIdCreatedDesc(String id);
}
