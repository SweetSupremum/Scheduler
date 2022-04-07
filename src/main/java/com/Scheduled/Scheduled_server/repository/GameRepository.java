package com.Scheduled.Scheduled_server.repository;

import com.Scheduled.Scheduled_server.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, String> {
    @Modifying
    @Query(value = "delete from Game g where g.isDeleted = true")
    void deleteAllByDeleted();
}
