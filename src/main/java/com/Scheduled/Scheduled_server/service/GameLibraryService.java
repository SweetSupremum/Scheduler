package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.mapping.GameMapper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameLibrary;
import com.Scheduled.Scheduled_server.repository.GameLibraryRepository;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameLibraryService {
    private final GameRepository gameRepository;
    private final GameLibraryRepository gameLibraryRepository;
    private final GameMapper gameMapper;


    public List<GameLibrary> add() {
        return gameLibraryRepository.saveAll(gameRepository.findAll().stream()
                .filter(game -> game.getId().hashCode() % 2 == 0)
                .peek(game -> game.setInLibrary(true))
                .map(game -> {
                    GameLibrary gameLibrary = new GameLibrary(game.getGameBase());
                    gameLibrary.setGameId(game.getId());
                    return gameLibrary;
                })
                .collect(Collectors.toList()));
    }

    public GameDto get(String gameId) {
        return gameMapper.toDto(gameRepository.findById(gameId).orElse(new Game()).getGameBase());
    }
}
