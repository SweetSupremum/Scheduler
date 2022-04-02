package com.Scheduled.Scheduled_server.controller;

import com.Scheduled.Scheduled_server.model.GameLibrary;
import com.Scheduled.Scheduled_server.service.GameLibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
@CrossOrigin
public class GameLibraryController {
    private final GameLibraryService gameLibraryService;

    @GetMapping("/{gameId}")
    public ResponseEntity<?> get(@PathVariable String gameId) {
        GameLibrary gameLibrary = gameLibraryService.get(gameId);
        if (gameLibrary == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameLibrary);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<GameLibrary> gameDtos = gameLibraryService.getAll();
        if (gameDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameDtos);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody GameLibrary gameLibrary) {
        gameLibraryService.add(gameLibrary);
        return ResponseEntity.created(URI.create(String.format("library/%s", gameLibrary.getGameId()))).body(gameLibrary);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete() {
        gameLibraryService.delete();
        return ResponseEntity.noContent().build();
    }
}
