package com.Scheduled.Scheduled_server.mapping;

import com.Scheduled.Scheduled_server.dto.GameHistoryDto;
import com.Scheduled.Scheduled_server.model.GameHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface GameHistoryMapper {

    @Mappings({
            @Mapping(target = "created", source = "gameHistoryId.created"),
            @Mapping(target = "gameId", source = "gameHistoryId.gameId")
    })
    GameHistoryDto toDto(GameHistory gameHistory);

    List<GameHistoryDto> toDtos(List<GameHistory> gameHistories);

}
