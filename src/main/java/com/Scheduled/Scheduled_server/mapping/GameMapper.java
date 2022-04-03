package com.Scheduled.Scheduled_server.mapping;


import com.Scheduled.Scheduled_server.dto.GameDto;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameHistory;
import com.Scheduled.Scheduled_server.model.GameHistoryId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GameMapper {

    GameDto toDto(Game game, boolean inLibrary);

    GameHistory toGameHistory(Game game, GameHistoryId gameHistoryId);

    @Mapping(target = "id", source = "gameHistoryId.gameId")
    Game toGame(GameHistory gameHistory);
}

