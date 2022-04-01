package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameLibraryDto;
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


    public List<GameLibrary> init() {
        return gameLibraryRepository.saveAll(gameRepository.findAll().stream()
                .filter(game -> game.getId().hashCode() % 8 == 0)
                .map(game -> new GameLibrary(game.getId()))
                .collect(Collectors.toList()));
    }

    public GameLibraryDto get(String gameId) {
        List<String> gamesInLibrary = gameLibraryRepository
                .findAll().stream().map(GameLibrary::getGameId)
                .collect(Collectors.toList());
        Game game = gameRepository.findById(gameId).orElse(new Game());
        return gamesInLibrary.contains(game.getId()) ? gameMapper.gameToGameLibraryDto(game.getGameBase()) : null;
    }

    public List<GameLibraryDto> getAll() {
        List<String> gamesInLibrary = gameLibraryRepository.findAll().stream().map(GameLibrary::getGameId).collect(Collectors.toList());
        return gameMapper.gameToGameLibraryDtos(gameRepository
                .findAll()
                .stream()
                .filter(game -> gamesInLibrary.contains(game.getId()))
                .map(Game::getGameBase).collect(Collectors.toList()));
    }

    public void delete() {
        gameLibraryRepository.deleteAllInBatch();
    }
}
