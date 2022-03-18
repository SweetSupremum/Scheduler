package com.Scheduled.Scheduled_server.mapping;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface GameMapper {
    GameDto toDto(Game game);

    List<GameDto> toDto(List<Game> games);

    Game gameToGame(GameDto gameDto, @MappingTarget Game game);

    Game dtoToGame(GameDto gameDto);
}

