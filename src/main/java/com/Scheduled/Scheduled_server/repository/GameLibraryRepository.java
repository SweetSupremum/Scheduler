package com.Scheduled.Scheduled_server.repository;

import com.Scheduled.Scheduled_server.model.GameLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameLibraryRepository extends JpaRepository<GameLibrary, Long> {
Optional<GameLibrary> findByGameId (String gameId);
}
