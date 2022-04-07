package com.Scheduled.Scheduled_server.controller;


import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameServiceImpl gameService;

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<?> get(@PathVariable String id) {
        GameDto gameDto = gameService.get(id);
        if (gameDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameDto);
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getAllInLibraries(@RequestParam("in_library") boolean inLibrary) {
        List<GameDto> gameDtos = gameService.getAllInLibraries(inLibrary);
        if (gameDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameDtos);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
