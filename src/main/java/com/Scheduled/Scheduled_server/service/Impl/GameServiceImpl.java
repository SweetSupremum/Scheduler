package com.Scheduled.Scheduled_server.service.Impl;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.error.custom_exception.GameNotFoundException;
import com.Scheduled.Scheduled_server.helper.GameHelper;
import com.Scheduled.Scheduled_server.mapping.GameMapper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameHistory;
import com.Scheduled.Scheduled_server.repository.GameHistoryRepository;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import com.Scheduled.Scheduled_server.service.GameService;
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
    public void add(GameDto gameDto) {
        gameRepository.findByGameBaseName(gameDto.getGameBase().getName())
                .ifPresentOrElse((item) -> gameRepository.save(gameMapper.gameToGame(gameDto, item)),
                        () -> gameRepository.save(gameMapper.dtoToGame(gameDto)));
    }

    @Override
    public GameDto get(String id) {
        return gameMapper.toDto(gameRepository.findById(id)
                .orElseThrow(GameNotFoundException::new));
    }

    @Override
    public List<GameDto> getAll() {
        return gameMapper.toDto(Optional.of(gameRepository.findAll())
                .orElseThrow(GameNotFoundException::new));
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
                .findFirstByGameBaseLinkOrderByIdDesc(game.getGameBase().getLink())
                .map(gameHistory -> !gameMapper.gameHistoryToGame(gameHistory).equals(game))
                .orElse(false);

    }

    @Transactional
    public boolean isAdd(Game game) {
        return !gameHistoryRepository.existsByGameBaseLink(game.getGameBase().getLink());
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
            gameHistoryRepository.saveAll(update.parallelStream().filter(GameHelper::isValidGame)
                    .map(game ->
                            gameMapper
                                    .updated(game, currentDate, gameHistoryRepository
                                            .getFirstByGameBaseLinkOrderByIdDesc(game.getGameBase().getLink())
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