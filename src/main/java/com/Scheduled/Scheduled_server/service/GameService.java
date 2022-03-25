package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameDto;

import java.util.List;

public interface GameService {


    GameDto get(String id);

    List<GameDto> getAll();

    void delete(String id);

}
