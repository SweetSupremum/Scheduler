package com.Scheduled.Scheduled_server.mapping;

import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface GameMapper {
    @Mapping(target = "gameBase",source = "game.gameBase")
    GameDto toDto(Game game);
    @Mapping(target = "gameBase",source = "game.gameBase")
    List<GameDto> toDto(List<Game> games);
    @Mapping(target = "gameBase",source = "game.gameBase")
    Game gameToGame(GameDto gameDto, Game game);
    @Mapping(target = "gameBase",source = "gameBase")
    Game dtoToGame(GameDto gameDto);

}

