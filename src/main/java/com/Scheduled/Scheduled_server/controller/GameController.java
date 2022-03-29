package com.Scheduled.Scheduled_server.controller;


import com.Scheduled.Scheduled_server.service.GameServiceImpl;
import com.Scheduled.Scheduled_server.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameServiceImpl gameService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseHelper.get(gameService.get(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseHelper.getAll(gameService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        gameService.delete(id);
        return ResponseHelper.delete();
    }
}
