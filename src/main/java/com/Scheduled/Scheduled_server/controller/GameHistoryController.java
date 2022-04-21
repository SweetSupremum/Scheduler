package com.Scheduled.Scheduled_server.controller;

import com.Scheduled.Scheduled_server.dto.GameHistoryDto;
import com.Scheduled.Scheduled_server.service.GameHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class GameHistoryController {
    private final GameHistoryService gameHistoryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        List<GameHistoryDto> gameHistoryDtos = gameHistoryService.get(id);
        if (gameHistoryDtos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(gameHistoryDtos);
    }
}
