package com.Scheduled.Scheduled_server.repository;

import com.Scheduled.Scheduled_server.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    Optional<Game> findByGameBaseName(String name);
}
