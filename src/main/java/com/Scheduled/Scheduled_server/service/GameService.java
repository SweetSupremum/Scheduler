package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.mapping.GameMapper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final GameHistoryService gameHistoryService;
    private final EpicGamesClient epicGamesClient;
    private final GameLibraryService gameLibraryService;
    @Autowired
    private GameService gameService;

    public GameDto get(String id) {
        return gameRepository
                .findById(id)
                .map((game -> gameMapper.toDto(game, gameLibraryService.getAllIds().contains(game.getId()))))
                .orElse(null);

    }

    public List<GameDto> getAllInLibraries(boolean inLibrary) {
        List<String> gameIds = gameLibraryService.getAllIds();
        return gameRepository
                .findAll()
                .stream()
                .map(game -> gameMapper.toDto(game, gameIds.contains(game.getId())))
                .filter(gameDto -> gameDto.isInLibrary() == inLibrary)
                .collect(toList());


    }

    public void delete(String id) {
        gameRepository.deleteById(id);
    }

    public void rebootGames(ZonedDateTime currentDate) throws IOException {
        List<Game> oldGames = gameRepository.findAll();
        List<Game> newGames = epicGamesClient.loadGames();
        List<String> oldIds = oldGames.stream().map(Game::getId).collect(toList());
        List<Game> update = newGames.stream().filter(game -> !oldGames.contains(game) && oldIds.contains(game.getId())).collect(toList());
        List<String> updateIds = update.stream().map(Game::getId).collect(toList());
        List<Game> addOrUpdate = newGames.stream().filter(game -> !oldGames.contains(game)).collect(toList());
        List<Game> add = newGames.stream().filter(game -> !oldGames.contains(game) && !oldIds.contains(game.getId())).collect(toList());
        gameService.reboot(oldGames, addOrUpdate, currentDate, updateIds);
        update.forEach(game -> log.info("i update -> {}", game));
        add.forEach(game -> log.info("i add -> {}", game));
    }

    @Transactional
    public void reboot(List<Game> oldGames, List<Game> addOrUpdate, ZonedDateTime currentDate, List<String> updateIds) {
        oldGames.stream().filter(game -> updateIds.contains(game.getId())).peek(game -> game.setDeleted(true)).close();
        gameRepository.deleteAllByDeleted();
        gameRepository.saveAll(addOrUpdate);
        gameHistoryService.saveAllGameHistories(addOrUpdate, currentDate);
    }


}