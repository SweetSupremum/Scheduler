package com.Scheduled.Scheduled_server.repository;

import com.Scheduled.Scheduled_server.model.GameLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameLibraryRepository extends JpaRepository<GameLibrary, String> {
}
