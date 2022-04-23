package com.Scheduled.Scheduled_server.controller;

import com.Scheduled.Scheduled_server.dto.GameLibraryDto;
import com.Scheduled.Scheduled_server.service.GameLibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
@CrossOrigin
public class GameLibraryController {
    private final GameLibraryService gameLibraryService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody GameLibraryDto gameLibrary) {
        gameLibraryService.add(gameLibrary);
        return ResponseEntity
                .created(URI.create(String.format("library/%s", gameLibrary.getGameId())))
                .body(gameLibrary);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete() {
        gameLibraryService.delete();
        return ResponseEntity.noContent().build();
    }
}
