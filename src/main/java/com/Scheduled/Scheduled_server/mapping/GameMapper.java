package com.Scheduled.Scheduled_server.mapping;


import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.dto.GameLibraryDto;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameBase;
import com.Scheduled.Scheduled_server.model.GameHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper
public interface GameMapper {
    GameDto toDto(GameBase game);

    List<GameDto> toDtos(List<GameBase> games);

    @Mapping(target = "inLibrary", constant = "true")
    GameLibraryDto gameToGameLibraryDto(GameBase gameBase);

    List<GameLibraryDto> gameToGameLibraryDtos(List<GameBase> gameBase);

    @Mapping(target = "id", source = "game.gameBase")
    GameHistory create(Game game, ZonedDateTime created);

}

