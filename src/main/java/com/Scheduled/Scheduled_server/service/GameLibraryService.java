package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.GameLibraryDto;
import com.Scheduled.Scheduled_server.error.advice.custom.AlreadyInLibraryException;
import com.Scheduled.Scheduled_server.error.advice.custom.GameNotFoundException;
import com.Scheduled.Scheduled_server.mapping.GameLibraryMapper;
import com.Scheduled.Scheduled_server.model.Customer;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameLibrary;
import com.Scheduled.Scheduled_server.repository.CustomerRepository;
import com.Scheduled.Scheduled_server.repository.GameLibraryRepository;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GameLibraryService {

    private final GameRepository gameRepository;
    private final GameLibraryRepository gameLibraryRepository;
    private final CustomerRepository customerRepository;
    private final GameLibraryMapper mapper;

    public List<String> getAllIds() {
        return gameLibraryRepository.findAllByCustomerUserName(SecurityContextHolder
                .getContext()
                .getAuthentication().getName()).stream().map(GameLibrary::getGameId).collect(toList());
    }


    public void delete() {
        gameLibraryRepository.deleteAllInBatch();
    }

    public void add(GameLibraryDto gameLibraryDto) {

        Customer customer = customerRepository.findByUserName(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Not Auth");
        });
        String gameId = gameRepository
                .findById(gameLibraryDto.getGameId())
                .map(Game::getId).orElseThrow(GameNotFoundException::new);
        gameLibraryRepository.findById(gameId)
                .ifPresentOrElse((__) -> {
                            throw new AlreadyInLibraryException();
                        },
                        () -> gameLibraryRepository.save(mapper.toDto(gameLibraryDto, customer))
                );

    }
}
