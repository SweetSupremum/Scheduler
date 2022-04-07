package com.Scheduled.Scheduled_server.mapping;

import com.Scheduled.Scheduled_server.controller.GameLibraryDto;
import com.Scheduled.Scheduled_server.model.Customer;
import com.Scheduled.Scheduled_server.model.GameLibrary;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface GameLibraryMapper {
    GameLibrary toDto (GameLibraryDto gameLibraryDto, Customer customer);
    GameLibraryDto dtoTo (GameLibrary gameLibrary);
    List<GameLibraryDto> dtosTo (List<GameLibrary> gameLibraries);
}
