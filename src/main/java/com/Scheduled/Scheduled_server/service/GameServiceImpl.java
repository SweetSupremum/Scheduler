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


    public void loadGames(Date currentDate) throws IOException {
        List<Game> currentGames = epicGamesClient.loadGames();
        List<Game> oldGames = gameRepository.findAll();
        List<Game> addOrUpdate = currentGames.stream().filter(game -> !oldGames.contains(game)).collect(Collectors.toList());
        List<Game> update = oldGames
                .stream()
                .filter(game ->
                        addOrUpdate
                                .stream()
                                .map(Game::getId)
                                .collect(Collectors.toList())
                                .contains(game.getId()))
                .collect(Collectors.toList());
        List<Game> add = addOrUpdate.stream().filter(game -> !update.contains(game)).collect(Collectors.toList());
        gameRepository.deleteAll(oldGames.stream().filter(game -> !currentGames.contains(game)).collect(Collectors.toList()));
        gameRepository.saveAll(addOrUpdate);
        addGame(add, currentDate);
        updateGame(update, currentDate);

    }

    private void updateGame(List<Game> update, Date currentDate) {
        if (!update.isEmpty()) gameHistoryRepository.saveAll(update.parallelStream()
                .map(game -> gameMapper
                        .createOrUpdate(
                                game,
                                gameHistoryRepository
                                        .getFirstByIdLinkOrderByUpdatedAsc(game.getGameBase().getLink()).getCreated(),
                                currentDate)
                ).collect(Collectors.toList()));
    }

    private void addGame(List<Game> addGames, Date created) {
        if (!addGames.isEmpty()) gameHistoryRepository.saveAll(addGames.parallelStream()
                .map(game -> gameMapper
                        .createOrUpdate(game, created, created)
                ).collect(Collectors.toList()));
    }


}