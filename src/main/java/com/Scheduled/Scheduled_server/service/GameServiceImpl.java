package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.mapping.GameMapper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.parser.EpicGamesClient;
import com.Scheduled.Scheduled_server.repository.GameHistoryRepository;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import com.Scheduled.Scheduled_server.utils.GameServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
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
                .orElse(new Game()).getGameBase());
    }

    public List<GameDto> getAll() {
        return gameMapper.toDtos(gameRepository.findAll()
                .stream().map(Game::getGameBase).collect(Collectors.toList()));

    }

    public void delete(String id) {
        gameRepository.deleteById(id);
    }


    public void rebootGames(ZonedDateTime currentDate) throws IOException {
        Pair<List<Game>, List<Game>> rebootList = GameServiceHelper.rebootLists(epicGamesClient.loadGames(), gameRepository.findAll());
        List<Game> leaveGamesLists = rebootList.getSecond();
        gameRepository.deleteAll(rebootList.getFirst());
        gameRepository.saveAll(leaveGamesLists);
        saveAllGameHistories(leaveGamesLists, currentDate);
    }

    public void saveAllGameHistories(List<Game> games, ZonedDateTime created) {
        if (!games.isEmpty()) gameHistoryRepository.saveAll(games.parallelStream()
                .map(game -> gameMapper
                        .create(game, created)
                ).collect(Collectors.toList()));
    }
}