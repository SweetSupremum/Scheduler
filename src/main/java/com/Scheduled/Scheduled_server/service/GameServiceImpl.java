package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.error.custom_exception.GameNotFoundException;
import com.Scheduled.Scheduled_server.mapping.GameMapper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.parser.EpicGamesClient;
import com.Scheduled.Scheduled_server.repository.GameHistoryRepository;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameServiceImpl {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final GameHistoryRepository gameHistoryRepository;
    private final EpicGamesClient epicGamesClient;

    public GameDto get(String id) {
        return gameMapper.toDto(gameRepository.findById(id)
                .orElseThrow(GameNotFoundException::new).getGameBase());
    }

    public List<GameDto> getAll() {
        return Optional.of(gameMapper.toDtos(gameRepository.findAll()
                .stream().map(Game::getGameBase)
                .collect(Collectors.toList())))
                .orElseThrow(GameNotFoundException::new);
    }

    public void delete(String id) {
        gameRepository
                .findById(id)
                .ifPresentOrElse((__) -> gameRepository.deleteById(id), GameNotFoundException::new);
    }


    @Transactional
    public void loadGames(Date currentDate) throws IOException {
        List<Game> currentGames = epicGamesClient.loadGames();
        addGame(currentGames.parallelStream()
                .filter(
                        game ->
                                !gameRepository
                                        .existsById(game.getId()))
                .collect(Collectors.toList()), currentDate);
        updateGame(currentGames
                .parallelStream()
                .filter(
                        game ->
                                gameRepository
                                        .findById(game.getId())
                                        .map(oldGame -> !oldGame.equals(game))
                                        .orElse(false))
                .collect(Collectors.toList()), currentDate);
        gameRepository.deleteAllInBatch();
        gameRepository.saveAll(currentGames);
    }

    private void updateGame(List<Game> update, Date currentDate) {
        if (!update.isEmpty()) gameHistoryRepository.saveAll(update.parallelStream()
                .map(game -> gameMapper
                        .updated(game, currentDate, gameHistoryRepository
                                .getByIdLink(game.getGameBase().getLink())
                                .getCreated())
                ).collect(Collectors.toList()));
    }

    private void addGame(List<Game> addGames, Date currentDate) {
        if (!addGames.isEmpty()) gameHistoryRepository.saveAll(addGames.parallelStream()
                .map(game -> gameMapper
                        .created(game, currentDate, currentDate)
                ).collect(Collectors.toList()));
    }

}