package com.Scheduled.Scheduled_server.controller;





import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.service.Impl.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GameController {
    private final GameServiceImpl gameService;

    @PostMapping("/games")
    public HttpStatus add(@RequestBody GameDto game) {
        gameService.add(game);
        return HttpStatus.CREATED;
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<GameDto> get(@PathVariable String id) {
        return new ResponseEntity<>(gameService.get(id), HttpStatus.OK);
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameDto>> getAll() {
        return new ResponseEntity<>(gameService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/games/{id}")
    public HttpStatus delete(@PathVariable String id) {
        gameService.delete(id);
        return HttpStatus.NO_CONTENT;
    }
}
