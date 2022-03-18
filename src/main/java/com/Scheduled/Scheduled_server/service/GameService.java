package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameDto;

import java.util.List;

public interface GameService {
    void add(GameDto game);

    GameDto get(Long id);

    List<GameDto> getAll();

    void delete(Long id);

}
