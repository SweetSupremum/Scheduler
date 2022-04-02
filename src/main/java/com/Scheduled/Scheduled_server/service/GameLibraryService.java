package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.error.advice.custom.AlreadyInLibraryException;
import com.Scheduled.Scheduled_server.error.advice.custom.GameNotFoundException;
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


    public GameLibrary get(String gameId) {
        return gameLibraryRepository.findById(gameId).orElse(null);
    }

    public List<GameLibrary> getAll() {
        return gameLibraryRepository.findAll();
    }

    public List<String> getAllIds() {
        return gameLibraryRepository.findAll().stream().map(GameLibrary::getGameId).collect(Collectors.toList());
    }

    public void delete() {
        gameLibraryRepository.deleteAllInBatch();
    }

    public void add(GameLibrary gameLibrary) {
        String gameId = gameRepository
                .findById(gameLibrary.getGameId())
                .map(Game::getId).orElseThrow(GameNotFoundException::new);
        gameLibraryRepository.findById(gameId)
                .ifPresentOrElse((__) -> {
                            throw new AlreadyInLibraryException();
                        },
                        () -> gameLibraryRepository.save(gameLibrary));

    }
}
