package com.Scheduled.Scheduled_server.service.Impl;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.error.custom_exception.GameNotFoundException;
import com.Scheduled.Scheduled_server.mapping.GameMapper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import com.Scheduled.Scheduled_server.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public void add(GameDto gameDto) {
        gameRepository.findByName(gameDto.getName())
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
        return  gameRepository.findById(game.getId())
                .map(value -> !value.equals(game))
                .orElse(false);
    }

    @Transactional
    public boolean isAdd(Game game) {
        return !gameRepository.existsById(game.getId());
    }

    public void saveAll(List<Game> games) {
        if (!games.isEmpty()) gameRepository.saveAll(games);
    }

    public long gamesCount() {
        return gameRepository.findAll().size();
    }
}