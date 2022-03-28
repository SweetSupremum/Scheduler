package com.Scheduled.Scheduled_server.repository;

import com.Scheduled.Scheduled_server.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {

}
