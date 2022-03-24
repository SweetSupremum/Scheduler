package com.Scheduled.Scheduled_server.service.Impl;


import com.Scheduled.Scheduled_server.model.GameHistory;
import com.Scheduled.Scheduled_server.repository.GameHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GameHistoryServiceImpl {
    private final GameHistoryRepository gameHistoryRepository;

    public void save(GameHistory gameHistory) {
        gameHistoryRepository.save(gameHistory);
    }

}
