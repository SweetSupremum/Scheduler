package com.Scheduled.Scheduled_server.controller;


import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getAll() {
        List<GameDto> gameDtos = gameService.getAll();
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
