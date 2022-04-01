package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.error.advice.custom.GameNotFoundException;
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
                .peek(game -> game.setInLibrary(true))
                .map(game -> {
                    GameLibrary gameLibrary = new GameLibrary(game.getGameBase());
                    gameLibrary.setGameId(game.getId());
                    return gameLibrary;
                })
                .collect(Collectors.toList()));
    }

    public GameDto add(GameDto gameDto) {
        Game game = gameRepository
                .findByGameBaseLink(gameDto.getLink())
                .orElseThrow(GameNotFoundException::new);
        return gameMapper.toDto(gameLibraryRepository
                .save(gameMapper
                        .gameToGameLibrary(game.getId(), game.getGameBase())).getGameBase());
    }

    public GameDto get(String gameId) {
        return gameMapper.toDto(gameLibraryRepository.findByGameId(gameId).orElse(new GameLibrary()).getGameBase());
    }

    public List<GameDto> getAll() {
        return gameLibraryRepository
                .findAll().stream().map(gameLibrary -> gameMapper.toDto(gameLibrary.getGameBase())).collect(Collectors.toList());
    }

    public void delete() {
        gameLibraryRepository.deleteAllInBatch();
    }
}
