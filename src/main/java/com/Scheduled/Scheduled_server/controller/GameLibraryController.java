package com.Scheduled.Scheduled_server.controller;

import com.Scheduled.Scheduled_server.model.GameLibrary;
import com.Scheduled.Scheduled_server.service.GameLibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
@CrossOrigin
public class GameLibraryController {
    private final GameLibraryService gameLibraryService;

    @PostMapping("/init")
    public ResponseEntity<?> add() {
        return ResponseEntity.ok(gameLibraryService.init());
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<?> get(@PathVariable String gameId) {
        GameLibrary gameLibrary = gameLibraryService.get(gameId);
        return gameLibrary != null ? ResponseEntity.ok(gameLibrary) : ResponseEntity.notFound().build();
    }

    @GetMapping("/games")
    public ResponseEntity<?> getAll() {
        List<GameLibrary> gameDtos = gameLibraryService.getAll();
        return !gameDtos.isEmpty() ? ResponseEntity.ok(gameDtos) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody GameLibrary gameLibrary) {
        return ResponseEntity.ok(gameLibraryService.add(gameLibrary));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete() {
        gameLibraryService.delete();
        return ResponseEntity.noContent().build();
    }
}
