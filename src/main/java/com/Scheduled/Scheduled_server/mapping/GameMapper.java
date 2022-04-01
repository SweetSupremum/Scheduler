package com.Scheduled.Scheduled_server.mapping;


import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper
public interface GameMapper {
    GameDto toDto(Game game, boolean inLibrary);

    @Mapping(target = "id", source = "game.gameBase")
    GameHistory create(Game game, ZonedDateTime created);

}

