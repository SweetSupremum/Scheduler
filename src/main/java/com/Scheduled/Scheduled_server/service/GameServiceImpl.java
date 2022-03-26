package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.error.custom_exception.GameNotFoundException;
import com.Scheduled.Scheduled_server.mapping.GameMapper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.repository.GameHistoryRepository;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final GameHistoryRepository gameHistoryRepository;


    @Override
    public GameDto get(String id) {
        return gameMapper.toDto(gameRepository.findById(id)
                .orElseThrow(GameNotFoundException::new).getGameBase());
    }

    @Override
    public List<GameDto> getAll() {
        return Optional.of(gameMapper.toDtos(gameRepository.findAll()
                .stream().map(Game::getGameBase)
                .collect(Collectors.toList())))
                .orElseThrow(GameNotFoundException::new);
    }

    @Override
    public void delete(String id) {
        gameRepository
                .findById(id)
                .ifPresentOrElse((__) -> gameRepository.deleteById(id), GameNotFoundException::new);
    }

    @Transactional
    public boolean isUpdate(Game game) {
        return gameHistoryRepository
                .findById(game.getGameBase())
                .map(gameHistory -> !gameHistory.getId().equals(game.getGameBase()))
                .orElse(false);

    }

    @Transactional
    public boolean isAdd(Game game) {
        return !gameHistoryRepository.existsById(game.getGameBase());
    }

    @Transactional
    public void saveAll(List<Game> update, List<Game> add, List<Game> games, Date currentDate) {
        if (!games.isEmpty()) gameRepository.saveAll(games);

        if (!add.isEmpty()) {
            gameHistoryRepository.saveAll(add.parallelStream().map(game ->
                    gameMapper.created(game, currentDate, currentDate)
            ).collect(Collectors.toList()));
        }
        if (!update.isEmpty()) {
            gameHistoryRepository.saveAll(update.parallelStream()
                    .map(game ->
                            gameMapper
                                    .updated(game, currentDate, gameHistoryRepository
                                            .getByIdLink(game.getGameBase().getLink())
                                            .getCreated())
                    ).collect(Collectors.toList()));
        }
    }

    public void deleteAllGames() {
        if (!gameRepository.findAll().isEmpty()) gameRepository.deleteAllInBatch();
    }

    public long gamesCount() {
        return gameRepository.findAll().size();
    }
}