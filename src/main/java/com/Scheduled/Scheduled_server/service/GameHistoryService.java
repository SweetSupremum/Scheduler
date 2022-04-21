package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameHistoryDto;
import com.Scheduled.Scheduled_server.mapping.GameHistoryMapper;
import com.Scheduled.Scheduled_server.mapping.GameMapper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameHistoryId;
import com.Scheduled.Scheduled_server.repository.GameHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GameHistoryService {
    private final GameHistoryRepository repository;
    private final GameHistoryMapper mapper;
    private final GameMapper gameMapper;

    public List<GameHistoryDto> get(String id) {
        return mapper
                .toDtos(repository
                        .findAllByGameHistoryId_GameIdOrderByGameHistoryIdCreatedDesc(id)
                        .orElse(null));
    }

    public void saveAllGameHistories(List<Game> games, ZonedDateTime created) {

        if (!games.isEmpty()) repository.saveAll(games.stream()
                .map(game -> gameMapper.toGameHistory(game, new GameHistoryId(created,game.getId())))
                .collect(toList()));

    }

}
